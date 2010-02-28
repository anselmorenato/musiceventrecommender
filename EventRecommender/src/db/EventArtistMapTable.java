package db;

import java.io.File;
import java.sql.PreparedStatement;

import music.MusicItem;

public class EventArtistMapTable extends DatabaseTable {

	public EventArtistMapTable(File db) throws DatabaseException {
		super(db);
	}

	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE eventartistmap" +
		"( " +
		"event integer NOT NULL," +
		"artist character(37) NOT NULL," +
		"PRIMARY KEY (event,artist)," +
		"FOREIGN KEY (event) REFERENCES events(id)" +
		"FOREIGN KEY (artist) REFERENCES artists(mbid)" +
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
