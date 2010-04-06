package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import music.Artist;
import music.MusicItem;
import music.MusicItemException;


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
	
	/**
	 * Creates an Artist object from a single entry in the result set
	 * @param rs - A result set
	 * @return - The artist
	 * @throws SQLException 
	 */
	public Artist makeArtist(ResultSet rs) throws SQLException {
		String mbid = rs.getString("mbid");
		String name = rs.getString("name");
		int playcount = rs.getInt("playcount");
		
		Artist a;
		try {
			a = new Artist(mbid, name);
		} catch (MusicItemException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		a.setPlaycount(playcount);
		return a;
	}

	/**
	 * Get an artist
	 * @param mbid - the artists mbid
	 * @return The artist
	 * @throws DatabaseException 
	 */
	public Artist getArtist(String mbid) throws DatabaseException {
		String sql = "SELECT * FROM artists " +
				"WHERE mbid = ?";
		
		ResultSet rs;
		
		
		try {
			PreparedStatement select = conn.prepareStatement(sql);
			select.setString(1, mbid);
			rs = select.executeQuery();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		Artist a = null;
		try {
			while(rs.next()) {
				a = makeArtist(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return a;
	}
	
	/** 
	 * Get at most limit artists which have the highest play count over all artists
	 * @param limit the maximum number of artists to return
	 * @return A list of most played artists
	 */
	public LinkedList<Artist> getMostPlayedArtists(int limit) throws DatabaseException {
		String sql = "SELECT * FROM Artists " +
				"ORDER BY playcount " +
				"LIMIT " + limit;
		
		ResultSet rs;
		LinkedList<Artist> top = new LinkedList<Artist>();
		
		try {
			Statement select = conn.createStatement();
			rs = select.executeQuery(sql);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			while(rs.next()) {
				Artist a = makeArtist(rs);
				if (a != null)
					top.add(a);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return top;
	}
	
	/** 
	 * Get all artists in the database
	 * @return A list of all artists
	 */
	public LinkedList<Artist> getAllArtists() throws DatabaseException {
		String sql = "SELECT * FROM Artists " +
				"ORDER BY name ";
		
		ResultSet rs;
		LinkedList<Artist> all = new LinkedList<Artist>();
		
		try {
			Statement select = conn.createStatement();
			rs = select.executeQuery(sql);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			while(rs.next()) {
				Artist a = makeArtist(rs);
				if (a != null)
					all.add(a);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return all;
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
