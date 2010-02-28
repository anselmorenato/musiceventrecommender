package db;

import java.io.File;
import java.sql.PreparedStatement;

import music.MusicItem;

public class EventsTable extends DatabaseTable {

	public EventsTable(File db) throws DatabaseException {
		super(db);
	}

	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE events" +
		"( " +
		"id integer NOT NULL," +
		"title VARCHAR(80) NOT NULL," +
		"venue integer NOT NULL," +
		"date text NOT NULL," +
		"decription text," +
		"website VARCHAR(80)," + 
		"ticketsite VARCHAR(80)," +
		"PRIMARY KEY (id)," +
		"FOREIGN KEY (venue) REFERENCES venues(id)" +
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
