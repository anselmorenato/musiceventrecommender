package schedulers;

import java.util.Calendar;
import java.util.Date;

import music.Location;
import recommendation.RecommendationGenerator;
import scanners.MusicImporter;
import db.Database;
import db.DatabaseException;
import Application.Preferences;


public class Scheduler {
	
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
	
	public Preferences getPreferences()
	{
		return config;
	}
	
	public String getDatPath() {
		return datPath;
	}

	public String getDirPath() {
		return dirPath;
	}

	public String getLibPath() {
		return libPath;
	}

	public int getTop() {
		return top;
	}
	
	public int getLocation() {
		return location;
	}

	public boolean isEvery() {
		return every;
	}

	public int getFilesys() {
		return filesys;
	}

	public int getRec() {
		return rec;
	}

	public Database getDb() {
		return db;
	}

	public static void main(String[] args)
	{
		Scheduler sch = new Scheduler();
		Schedulable directory = new MusicImporter(sch.getDb(),sch.getDirPath(),sch.getLibPath());
		
		Location local;
		switch(sch.getLocation()){
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
		
		Schedulable recommend = new RecommendationGenerator(sch.getDb(),local,sch.isEvery(),sch.getTop());
		
		//Scan directory
		Calendar now = Calendar.getInstance();
		Preferences pf = sch.getPreferences();
		switch(sch.getFilesys()){
		case 1:
			if(now.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
			{
				directory.run();
				pf.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthScan(now.get(Calendar.MONTH)+"");
				pf.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		case 2:
			if(now.get(Calendar.MONTH) != pf.getMonthScan())
			{
				directory.run();
				pf.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthScan(now.get(Calendar.MONTH)+"");
				pf.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		case 3:
			if(now.get(Calendar.YEAR) != pf.getYearScan())
			{
				directory.run();
				pf.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthScan(now.get(Calendar.MONTH)+"");
				pf.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		default:
			if(now.get(Calendar.DAY_OF_MONTH) != pf.getDayScan())
			{
				directory.run();
				pf.setDayScan(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthScan(now.get(Calendar.MONTH)+"");
				pf.setYearScan(now.get(Calendar.YEAR)+"");
			}
			break;
		}
		
		// Recommendations
		switch(sch.getRec()){
		case 1:
			if(now.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
			{
				recommend.run();
				pf.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthRec(now.get(Calendar.MONTH)+"");
				pf.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		case 2:
			if(now.get(Calendar.MONTH) != pf.getMonthRec())
			{
				recommend.run();
				pf.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthRec(now.get(Calendar.MONTH)+"");
				pf.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		case 3:
			if(now.get(Calendar.YEAR) != pf.getYearRec())
			{
				recommend.run();
				pf.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthRec(now.get(Calendar.MONTH)+"");
				pf.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		default:
			if(now.get(Calendar.DAY_OF_MONTH) != pf.getDayRec())
			{
				recommend.run();
				pf.setDayRec(now.get(Calendar.DAY_OF_MONTH)+"");
				pf.setMonthRec(now.get(Calendar.MONTH)+"");
				pf.setYearRec(now.get(Calendar.YEAR)+"");
			}
			break;
		}

		pf.writePreferences();
	}
}
