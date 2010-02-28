package db;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import music.Artist;
import music.MusicItem;



public class ArtistsTable extends DatabaseTable{

	public ArtistsTable(File db) throws DatabaseException {
		super(db);
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
	protected PreparedStatement getInsertStatement() throws SQLException {
		PreparedStatement prep = conn.prepareStatement(
				"insert into artists (mbid, name, playcount) " +
		"values (?, ?, ?)");

		return prep;
	}

	@Override
	protected PreparedStatement getUpdateStatement() throws SQLException {
		PreparedStatement prep = conn.prepareStatement(
				"update artists " +
				"set mbid = ?," +
				"name = ?," +
				"playcount = ? where mbid = ?");

		return prep;
	}

	@Override
	protected void populateInsert(PreparedStatement p, MusicItem item) throws SQLException {
		Artist a = (Artist) item;
		p.setString(1, a.getMBID());
		p.setString(2, a.getName());
		p.setInt(3, a.getPlaycount());
	}

	@Override
	protected void populateUpdate(PreparedStatement p, MusicItem item) throws SQLException {

		Artist a = (Artist) item;
		p.setString(1, a.getMBID());
		p.setString(2, a.getName());
		p.setInt(3, a.getPlaycount());
		p.setString(4, a.getMBID());
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
