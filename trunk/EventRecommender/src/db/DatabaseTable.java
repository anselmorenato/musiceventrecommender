package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import music.MusicItem;

public abstract class DatabaseTable {
	
	final protected Connection conn;
	protected PreparedStatement updateStat;
	protected PreparedStatement insertStat;
	
	public DatabaseTable(Connection dbconn, boolean createTable) throws DatabaseException{
		conn = dbconn;
		try {
			if (createTable) {
				create();
			}
			prepareStatements();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	/**
	 * Will not create tables if they don't exist.
	 * @param dbconn
	 * @throws DatabaseException
	 */
	public DatabaseTable(Connection dbconn) throws DatabaseException{
		conn = dbconn;
		try {
			prepareStatements();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	protected abstract String getCreateStatement();
	
	protected void prepareStatements() throws SQLException {
		this.updateStat = updateStatement();
		this.insertStat = insertStatement();
	}
	
	public void create() throws DatabaseException {
		String createSQL = getCreateStatement();
		try {
			Statement stat = conn.createStatement();
			stat.execute(createSQL);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
		
	protected abstract PreparedStatement insertStatement() throws SQLException;
	protected abstract PreparedStatement updateStatement() throws SQLException;
	
	public abstract int update(MusicItem item) throws DatabaseException;
	public abstract int insert(MusicItem item) throws DatabaseException;
	public abstract boolean contains(MusicItem item) throws DatabaseException;
	
	/**
	 * Closes the connection to the database
	 * This commits all changes
	 * @throws DatabaseException 
	 */
	public void closeConnection() throws DatabaseException {
		/* TODO
		 * should this close the connection or just the statements?
		 */
		
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}
}
