package Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Preferences {
	
	Properties configProperties;
	String configFilePath;
	String comments = "MusicEventRecommender settings";
	
	
	// Preference values
	private String databasePath;
	private String musicLibraryPath;
	private String itunesLibraryPath;
	private String topArtist;
	private String location;
	private String scanAll;
	private String scanFile;
	private String scanRec;
	private String ds1;
	private String ds2;
	private String ds3;
	private String dr1;
	private String dr2;
	private String dr3;
	
	// default values
	private String defaultDatabasePath = "musiceventdata.db";
	private String defaultMusicLibraryPath = "";
	private String defaultItunesLibraryPath = "";
	private String iTunesLibraryXML = "iTunes Music Library.xml";
	private String defaultTopArtist = "10";
	private String defaultLocation = "0";
	private String defaultScanAll = "true";
	private String defaultScanFile = "1";
	private String defaultScanRec = "0";
	
	// keys
	private String dbKey = "databasePath";
	private String musicKey = "musicLibrary";
	private String itunesKey = "itunesXML";
	private String topKey = "topArt";
	private String locKey = "location";
	private String allKey = "allEvents";
	private String filKey = "whenScan";
	private String recKey = "recommendations";
	private String ds1Key = "dayScan";
	private String ds2Key = "monthScan";
	private String ds3Key = "yearScan";
	private String dr1Key = "dayRec";
	private String dr2Key = "monthRec";
	private String dr3Key = "yearRec";
	
	public Preferences(String configPath) {
		this.configFilePath = configPath;
		configProperties = new Properties();
		
		setDefaultProperties();
		if (!readPreferences()) {
			writePreferences();
		}
	}
	
	public void generateDefaults()  {
		File currentDirectory = new File(".");
		String sep = File.separator;
		String base;
		try {
			base = currentDirectory.getCanonicalPath();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			base = "";
			sep = "";
		}
		
		this.databasePath = base + sep + "musiceventdata.db";
		this.musicLibraryPath = base;
		this.itunesLibraryPath = base + sep + this.iTunesLibraryXML;
		this.topArtist = "10";
		this.location = "0";
		this.scanAll = "true";
		this.scanFile = "1";
		this.scanRec = "0";
		this.ds1 = "-8";
		this.ds2 = "-1";
		this.ds3 = "0";
		this.dr1 = "-8";
		this.dr2 = "-1";
		this.dr3 = "0";
	}
	
	private void setDefaultProperties() {
		generateDefaults();
		
		configProperties.setProperty(this.dbKey, this.defaultDatabasePath);
		configProperties.setProperty(this.musicKey, this.defaultMusicLibraryPath);
		configProperties.setProperty(this.itunesKey, this.defaultItunesLibraryPath);
		configProperties.setProperty(topKey, defaultTopArtist);
		configProperties.setProperty(locKey, defaultLocation);
		configProperties.setProperty(allKey, defaultScanAll);
		configProperties.setProperty(filKey, defaultScanFile);
		configProperties.setProperty(recKey, defaultScanRec);
		configProperties.setProperty(ds1Key,"-8");
		configProperties.setProperty(ds2Key,"-1");
		configProperties.setProperty(ds3Key,"0");
		configProperties.setProperty(dr1Key,"-8");
		configProperties.setProperty(dr2Key,"-1");
		configProperties.setProperty(dr3Key,"0");
	}
	
	public boolean readPreferences() {
		
		File cFile = new File(this.configFilePath);
		if (!cFile.exists())
			return false;
		
		try {
			FileInputStream in = new FileInputStream(this.configFilePath);
			configProperties.load(in);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		this.databasePath = configProperties.getProperty(this.dbKey);
		this.musicLibraryPath = configProperties.getProperty(this.musicKey);
		this.itunesLibraryPath = configProperties.getProperty(this.itunesKey);
		this.topArtist = configProperties.getProperty(topKey);
		this.location = configProperties.getProperty(locKey);
		this.scanAll = configProperties.getProperty(allKey);
		this.scanFile = configProperties.getProperty(filKey);
		this.scanRec = configProperties.getProperty(recKey);
		this.ds1 = configProperties.getProperty(ds1Key);
		this.ds2 = configProperties.getProperty(ds2Key);
		this.ds3 = configProperties.getProperty(ds3Key);
		this.dr1 = configProperties.getProperty(dr1Key);
		this.dr2 = configProperties.getProperty(dr2Key);
		this.dr3 = configProperties.getProperty(dr3Key);
		
		return true;
	}
	
	public boolean writePreferences() {
		try {
			FileOutputStream out = new FileOutputStream(this.configFilePath);
			configProperties.store(out, this.comments);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	/**
	 * @return the databasePath
	 */
	public String getDatabasePath() {
		return databasePath;
	}

	/**
	 * @param databasePath the databasePath to set
	 */
	public void setDatabasePath(String databasePath) {
		this.databasePath = databasePath;
		configProperties.setProperty(this.dbKey, this.databasePath);
		
	}

	/**
	 * @return the musicLibraryPath
	 */
	public String getMusicLibraryPath() {
		return musicLibraryPath;
	}

	/**
	 * @param musicLibraryPath the musicLibraryPath to set
	 */
	public void setMusicLibraryPath(String musicLibraryPath) {
		this.musicLibraryPath = musicLibraryPath;
		configProperties.setProperty(this.musicKey, this.musicLibraryPath);
	}

	/**
	 * @return the itunesLibraryPath
	 */
	public String getItunesLibraryPath() {
		return itunesLibraryPath;
	}

	/**
	 * @param itunesLibraryPath the itunesLibraryPath to set
	 */
	public void setItunesLibraryPath(String itunesLibraryPath) {
		this.itunesLibraryPath = itunesLibraryPath;
		configProperties.setProperty(this.itunesKey, this.itunesLibraryPath);
	}
	
	
	/**
	 * @return the number of top artists
	 */
	public int getTopArtists(){
		return new Integer(topArtist);
	}
	
	public void setTopArtists(String num){
		topArtist = num;
		configProperties.setProperty(topKey, topArtist);
	}
	
	public int getLocation(){
		return new Integer(location);
	}
	
	public void setLocation(String where){
		location = where;
		configProperties.setProperty(locKey, location);
	}
	
	public boolean getAllEvents(){
		return new Boolean(scanAll);
	}
	
	public void setAllEvents(String all){
		scanAll = all;
		configProperties.setProperty(allKey, scanAll);
	}
	
	public int getScanFile(){
		return new Integer(scanFile);
	}
	
	public void setScanFile(String when){
		scanFile = when;
		configProperties.setProperty(filKey, scanFile);
	}
	
	public int getScanRec(){
		return new Integer(scanRec);
	}
	
	public void setScanRec(String when){
		scanRec = when;
		configProperties.setProperty(recKey, scanRec);
	}
	
	public int getDayScan()
	{
		return new Integer(ds1);
	}
	
	public int getDayRec()
	{
		return new Integer(dr1);
	}
	
	public int getMonthScan()
	{
		return new Integer(ds2);
	}
	
	public int getMonthRec()
	{
		return new Integer(dr2);
	}
	
	public int getYearScan()
	{
		return new Integer(ds3);
	}
	
	public int getYearRec()
	{
		return new Integer(dr3);
	}
	
	public void setDayScan(String num){
		ds1 = num;
		configProperties.setProperty(ds1Key, ds1);
	}
	
	public void setDayRec(String num){
		dr1 = num;
		configProperties.setProperty(dr1Key, dr1);
	}
	
	public void setMonthScan(String num){
		ds2 = num;
		configProperties.setProperty(ds2Key, ds2);
	}
	
	public void setMonthRec(String num){
		dr2 = num;
		configProperties.setProperty(dr2Key, dr2);
	}
	
	public void setYearScan(String num){
		ds3 = num;
		configProperties.setProperty(ds3Key, ds3);
	}
	
	public void setYearRec(String num){
		dr3 = num;
		configProperties.setProperty(dr3Key, dr3);
	}
}
