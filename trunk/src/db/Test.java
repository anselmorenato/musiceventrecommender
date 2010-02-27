package db;

import java.io.File;



public class Test {
	public static void main(String[] args) throws Exception {
		String fstr = "test.db";
		File dbfile = new File(fstr);
		DatabaseInitializer dbi = new DatabaseInitializer(dbfile);
		
		dbi.createTables();

	}
}