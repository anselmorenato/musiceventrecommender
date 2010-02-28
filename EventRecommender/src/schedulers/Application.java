package schedulers;

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
		
		MusicImporter im = new MusicImporter(db);
		
		String directory = "/Users/derek/Music/iTunes/iTunes Music";
		im.scanDirectory(directory);

	}

}
