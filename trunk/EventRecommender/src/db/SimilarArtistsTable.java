package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import music.Artist;
import music.MusicItem;

public class SimilarArtistsTable extends DatabaseTable {

	public SimilarArtistsTable(Connection conn) throws DatabaseException {
		super(conn);
	}
	
	public SimilarArtistsTable(Connection conn, boolean createTable) throws DatabaseException {
		super(conn, createTable);
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
	protected PreparedStatement insertStatement() throws SQLException {
		String SQL = "INSERT INTO similarartists " +
		"(artist, similar) VALUES" +
		"(?, ?)";

		PreparedStatement p = conn.prepareStatement(SQL);
		return p;
	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		String SQL = "UPDATE similarartists " +
		"SET similar = ?" +
		"WHERE artist = ?";

		PreparedStatement p = conn.prepareStatement(SQL);
		return p;
	}

	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = insertStatement();

			Artist a = (Artist) item;
			int rows = 0;
			
			for (Artist similar : a.getSimilarArtists()) {
				stat.setString(1, a.getMBID());
				stat.setString(2, similar.getMBID());
				rows += stat.executeUpdate();
			}
				
			return rows;

		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public int update(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = updateStatement();

			Artist a = (Artist) item;
			int rows = 0;
			
			for (Artist similar : a.getSimilarArtists()) {
				stat.setString(1, similar.getMBID());
				stat.setString(2, a.getMBID());
				rows += stat.executeUpdate();
			}
				
			return rows;

		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

}
