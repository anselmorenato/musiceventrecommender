package Application;

import scanners.MusicImporter;
import db.Database;
import db.DatabaseException;

public class Application {
	
	private String configFile = "config.txt";
	private Preferences prefs;
	
	public Application() {
		prefs = new Preferences(configFile);
	}
	
	public void run() {
		/* TODO:
		 * - run configuration file
		 * - delegate to the scheduler
		 */
		
		Database db = null;
		
		try {
			db = new Database(prefs.getDatabasePath());
		} catch (DatabaseException e) {
			System.err.println("Database error: " + e.getMessage());
		}
		
		String musicdir = prefs.getMusicLibraryPath();
		String itunes = prefs.getItunesLibraryPath();
		MusicImporter im = new MusicImporter(db, musicdir, itunes);
		im.run();
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}

}
