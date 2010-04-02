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
	
	// default values
	private String defaultDatabasePath;
	private String defaultMusicLibraryPath;
	private String defaultItunesLibraryPath;
	private String iTunesLibraryXML = "iTunes Music Library.xml";
	
	// keys
	private String dbKey = "databasePath";
	private String musicKey = "musicLibrary";
	private String itunesKey = "itunesXML";
	
	public Preferences(String configPath) {
		this.configFilePath = configPath;
		configProperties = new Properties();
		
		setDefaultProperties();
		if (!readPreferences()) {
			writePreferences();
		}
	}
	
	private void generateDefaults()  {
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
	}
	
	private void setDefaultProperties() {
		generateDefaults();
		
		configProperties.setProperty(this.dbKey, this.databasePath);
		configProperties.setProperty(this.musicKey, this.musicLibraryPath);
		configProperties.setProperty(this.itunesKey, this.itunesLibraryPath);
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
		configProperties.setProperty(this.dbKey, this.defaultDatabasePath);
		this.databasePath = databasePath;
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
		configProperties.setProperty(this.musicKey, defaultMusicLibraryPath);
		this.musicLibraryPath = musicLibraryPath;
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
		configProperties.setProperty(this.itunesKey, this.defaultItunesLibraryPath);
		this.itunesLibraryPath = itunesLibraryPath;
	}
}
