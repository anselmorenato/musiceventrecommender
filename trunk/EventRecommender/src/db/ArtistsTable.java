package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import music.Artist;
import music.MusicItem;


public class ArtistsTable extends DatabaseTable{
	
	public ArtistsTable(Connection c) throws DatabaseException {
		super(c);
	}
	
	public ArtistsTable(Connection c, boolean createTable) throws DatabaseException {
		super(c, createTable);
	}
	
	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE artists" +
		"( " +
		"mbid character(37) NOT NULL," +
		"name VARCHAR(80) NOT NULL," +
		"playcount integer DEFAULT 0," +
		"PRIMARY KEY (mbid)" +
		");";

		return SQL;
	}

	@Override
	protected PreparedStatement insertStatement() throws SQLException {
		
		PreparedStatement p = conn.prepareStatement(
				"insert into artists (mbid, name, playcount) " +
		"values (?, ?, ?)");
		
		return p;
	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		
		PreparedStatement p = conn.prepareStatement(
				"update artists SET " +
				"name = ?," +
				"playcount = ? where mbid = ?");
		
		return p;
	}
	
	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = insertStatement();
			
			Artist a = (Artist) item;
            stat.setString(1, a.getMBID());
            stat.setString(2, a.getName());
            stat.setInt(3, a.getPlaycount());
			
            int rows = stat.executeUpdate();
            return rows;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public int update(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = updateStatement();
			
			 Artist a = (Artist) item;
             stat.setString(1, a.getName());
             stat.setInt(2, a.getPlaycount());
             stat.setString(3, a.getMBID());

             int rows = stat.executeUpdate();
             return rows;
             
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		Artist a = (Artist) item;
		try {
			PreparedStatement prep = conn.prepareStatement(
					"select count(mbid) from artists where mbid=?");
			prep.setString(1, a.getMBID());

			ResultSet r = prep.executeQuery();
			int count = r.getInt(1);
			return (count > 0);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
