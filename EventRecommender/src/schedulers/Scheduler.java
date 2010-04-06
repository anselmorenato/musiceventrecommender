package schedulers;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import music.Location;
import recommendation.RecommendationGenerator;
import scanners.MusicImporter;
import db.Database;
import db.DatabaseException;
import Application.Preferences;


public class Scheduler extends TimerTask{
	
	private String datPath;
	private String dirPath;
	private String libPath;
	private int top;
	private int location;
	private boolean every;
	private int filesys;
	private int rec;
	
	private String dir = "config.txt";
	private Preferences config;
	private Database db;
	
	public Scheduler(){
		config = new Preferences(dir);
		config.readPreferences();
		datPath = config.getDatabasePath();
		dirPath = config.getMusicLibraryPath();
		libPath = config.getItunesLibraryPath();
		top = config.getTopArtists();
		location = config.getLocation();
		every = config.getAllEvents();
		filesys = config.getScanFile();
		rec = config.getScanRec();
		
		try {
			db = new Database(datPath);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		Schedulable directory = new MusicImporter(db,dirPath,libPath);
		
		Location local;
		switch(location){
		case 1:
			local = new Location("Quebec","Canada");
			break;
		case 2:
			local = new Location("Sherbrooke","Canada");
			break;
		case 3:
			local = new Location("Toronto","Canada");
			break;
		default:
			local = new Location("Montreal","Canada");
			break;
		}
		
		Schedulable recommend = new RecommendationGenerator(db,local,every,top);
		
		//Scan directory
		Calendar now = Calendar.getInstance();
		switch(filesys){
		case 1:
			if(Math.abs(now.get(Calendar.DAY_OF_MONTH)- config.getDayScan()) > 7)
			{
				directory.run();
				config.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthScan(now.get(Calendar.MONTH)+"");
				config.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		case 2:
			if(now.get(Calendar.MONTH) != config.getMonthScan())
			{
				directory.run();
				config.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthScan(now.get(Calendar.MONTH)+"");
				config.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		case 3:
			if(now.get(Calendar.YEAR) != config.getYearScan())
			{
				directory.run();
				config.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthScan(now.get(Calendar.MONTH)+"");
				config.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		default:
			if(now.get(Calendar.DAY_OF_MONTH) != config.getDayScan())
			{
				directory.run();
				config.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthScan(now.get(Calendar.MONTH)+"");
				config.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		}
		
		// Recommendations
		switch(rec){
		case 1:
			if(Math.abs(now.get(Calendar.DAY_OF_MONTH)- config.getDayScan()) > 7)
			{
				recommend.run();
				config.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthRec(now.get(Calendar.MONTH)+"");
				config.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		case 2:
			if(now.get(Calendar.MONTH) != config.getMonthRec())
			{
				recommend.run();
				config.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthRec(now.get(Calendar.MONTH)+"");
				config.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		case 3:
			if(now.get(Calendar.YEAR) != config.getYearRec())
			{
				recommend.run();
				config.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthRec(now.get(Calendar.MONTH)+"");
				config.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		default:
			if(now.get(Calendar.DAY_OF_MONTH) != config.getDayRec())
			{
				recommend.run();
				config.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				config.setMonthRec(now.get(Calendar.MONTH)+"");
				config.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		}

		config.writePreferences();
	}
	
}
