package schedulers;

import scanners.MusicImporter;
import db.Database;
import db.DatabaseException;

public class Application {

	/** Entry point for application
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = null;
		try {
			db = new Database("test.db");
		} catch (DatabaseException e) {
			System.err.println("Database error: " + e.getMessage());
		}
		
		String directory = "/Users/derek/Music/iTunes/iTunes Music";
		MusicImporter im = new MusicImporter(db, directory, "");
		im.run();

	}

}
