package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import music.Artist;
import music.Event;
import music.MusicItem;

public class EventArtistMapTable extends DatabaseTable {

	public EventArtistMapTable(Connection conn) throws DatabaseException {
		super(conn);
	}
	
	public EventArtistMapTable(Connection conn, boolean createTable) throws DatabaseException {
		super(conn, createTable);
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
	protected PreparedStatement insertStatement() throws SQLException {
		String sql = "INSERT INTO eventartistmap " +
		"(event, artist) " +
		"VALUES (?, ?)";
		PreparedStatement prep = conn.prepareStatement(sql);
		return prep;
	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		String sql = "UPDATE eventartistmap SET " +
		"event = ?," +
		"artist = ?";

		PreparedStatement prep = conn.prepareStatement(sql);
		return prep;
	}

	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = insertStatement();

			Event e = (Event) item;
			int rows = 0;

			for (Artist a : e.getArtists()) {
				stat.setInt(1, e.getID());
				stat.setString(1, a.getMBID());
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

			Event e = (Event) item;
			int rows = 0;

			for (Artist a : e.getArtists()) {
				stat.setString(1, a.getMBID());
				stat.setInt(1, e.getID());
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
