package db;

import java.io.File;
import java.sql.PreparedStatement;

import music.MusicItem;

public class SimilarArtistsTable extends DatabaseTable {

	public SimilarArtistsTable(File db) throws DatabaseException {
		super(db);
	}

	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE similarartists" +
		"( " +
		"artist character(37) NOT NULL," +
		"similar character(37) NOT NULL," +
		"PRIMARY KEY (artist,similar)," +
		"FOREIGN KEY (similar) REFERENCES artists(mbid)" +
		");";
		
		return SQL;
	}

	@Override
	protected PreparedStatement getInsertStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getUpdateStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void populateInsert(PreparedStatement p, MusicItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void populateUpdate(PreparedStatement p, MusicItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

}
