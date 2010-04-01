package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import music.MusicItem;
import music.Song;

public class SongsTable extends DatabaseTable {

	public SongsTable(Connection conn) throws DatabaseException {
		super(conn);
	}

	public SongsTable(Connection conn, boolean createTable) throws DatabaseException {
		super(conn, createTable);
	}
	
	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE songs" +
		"( " +
		"title VARCHAR(80) NOT NULL," +
		"album VARCHAR(80) NOT NULL," +
		"playcount integer DEFAULT 0," +
		"artist character(37) NOT NULL," +
		"PRIMARY KEY (title,album, artist)," +
		"FOREIGN KEY (artist) REFERENCES artists(mbid)" +
		");";
		return SQL;
	}

	@Override
	protected PreparedStatement insertStatement() throws SQLException {
		PreparedStatement prep = conn.prepareStatement(
				"insert into songs (title, playcount, artist, album) " +
				"values (?, ?, ?, ?);");

		return prep;
	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		PreparedStatement prep = conn.prepareStatement(
				"UPDATE songs SET " +
				"playcount = ?," +
				"artist = ? where title = ? AND album = ? AND artist = ?");

		return prep;
	}
	
	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = updateStatement();
			
			Song s = (Song) item;
			stat.setString(1, s.getTitle());
			stat.setInt(2, s.getPlaycount());
			stat.setString(3, s.getArtist().getMBID());
			stat.setString(4, s.getAlbum());
			
            int rows = stat.executeUpdate();
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
			
			Song s = (Song) item;
			stat.setInt(1, s.getPlaycount());
			stat.setString(2, s.getTitle());
			stat.setString(3, s.getAlbum());
			stat.setString(4, s.getArtist().getMBID());
			
            int rows = stat.executeUpdate();
            return rows;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		Song s = (Song) item;
		try {
			PreparedStatement prep = conn.prepareStatement(
					"select count(*) from songs where title=? " +
					"AND artist=? AND album=?");
			
			prep.setString(1, s.getTitle());
			prep.setString(2, s.getArtist().getMBID());
			prep.setString(3, s.getAlbum());
			
			ResultSet r = prep.executeQuery();
			int count = r.getInt(1);
			return (count > 0);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
