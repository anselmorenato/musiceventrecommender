package db;

import java.io.File;
import java.sql.PreparedStatement;

import music.MusicItem;

public class VenuesTable extends DatabaseTable {

	public VenuesTable(File db) throws DatabaseException {
		super(db);
	}

	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE venues" +
		"( " +
		"id integer NOT NULL," +
		"name VARCHAR(80) NOT NULL," +
		"city VARCHAR(80) NOT NULL," +
		"street VARCHAR(128) NOT NULL," +
		"postalcode VARCHAR(7) NOT NULL," +
		"latitude double," +
		"longitude double," +
		"PRIMARY KEY (id)" +
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
