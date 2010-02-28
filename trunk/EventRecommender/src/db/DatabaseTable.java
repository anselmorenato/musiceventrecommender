package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import music.MusicItem;

public abstract class DatabaseTable {
	private String dbPath;
	final protected Connection conn;
	
	public DatabaseTable(File db) throws DatabaseException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("CLASS NOT FOUND!");
			throw new DatabaseException(e.getMessage());
		}
		this.dbPath = "jdbc:sqlite:" + db.getAbsolutePath();
		
		
		try {
			this.conn = DriverManager.getConnection(dbPath);
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	protected abstract String getCreateStatement();
	
	public void create() throws DatabaseException {
		String createSQL = getCreateStatement();
		try {
			Statement stat = conn.createStatement();
			stat.execute(createSQL);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
		
	private int executeUpdate(PreparedStatement stat) throws DatabaseException {
		int rows = 0;
		
		try {
			rows = stat.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return rows;	
	}
	
	protected abstract PreparedStatement getInsertStatement() throws SQLException;
	protected abstract void populateInsert(PreparedStatement p, MusicItem item) throws SQLException;
	
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = getInsertStatement();
			populateInsert(stat, item);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return executeUpdate(stat);
	}
	
	protected abstract PreparedStatement getUpdateStatement() throws SQLException;
	protected abstract void populateUpdate(PreparedStatement p, MusicItem item) throws SQLException;
	
	public int update(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = getUpdateStatement();
			populateUpdate(stat, item);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return executeUpdate(stat);
	}
	
	public abstract boolean contains(MusicItem item) throws DatabaseException;
	
	/**
	 * Closes the connection to the database
	 * This commits all changes
	 * @throws DatabaseException 
	 */
	public void close() throws DatabaseException {
		try {
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				throw new DatabaseException(e1);
			}
			throw new DatabaseException(e);
		}
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
