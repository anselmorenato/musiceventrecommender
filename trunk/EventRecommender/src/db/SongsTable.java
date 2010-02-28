package db;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import music.Artist;
import music.MusicItem;
import music.Song;

public class SongsTable extends DatabaseTable {

	public SongsTable(File db) throws DatabaseException {
		super(db);
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
	protected PreparedStatement getInsertStatement() throws SQLException {
		PreparedStatement prep = conn.prepareStatement(
				"insert into songs (title, playcount, artist, album) " +
				"values (?, ?, ?, ?);");

		return prep;
	}

	@Override
	protected PreparedStatement getUpdateStatement() throws SQLException {
		PreparedStatement prep = conn.prepareStatement(
				"update songs " +
				"set title = ?," +
				"playcount = ?," +
				"album = ?," +
				"artist = ? where title = ? AND album = ? AND artist = ?");

		return prep;
	}

	@Override
	protected void populateInsert(PreparedStatement p, MusicItem item) throws SQLException {
		Song s = (Song) item;
		p.setString(1, s.getTitle());
		p.setInt(2, s.getPlaycount());
		p.setString(3, s.getArtist().getMBID());
		p.setString(4, s.getAlbum());

	}
	
	@Override
	protected void populateUpdate(PreparedStatement p, MusicItem item) throws SQLException {
		Song s = (Song) item;
		p.setString(1, s.getTitle());
		p.setInt(2, s.getPlaycount());
		p.setString(3, s.getAlbum());
		p.setString(4, s.getArtist().getMBID());
		
		p.setString(5, s.getTitle());
		p.setString(6, s.getAlbum());
		p.setString(7, s.getArtist().getMBID());
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
