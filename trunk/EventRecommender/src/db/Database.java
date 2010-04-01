package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import music.Artist;
import music.MusicItem;
import music.Song;

public class Database {
	final private File dbfile;
	
	
	public Database(String path) throws DatabaseException {
		
		File db = new File(path);
		this.dbfile = db;
		
		if (!db.exists()) {
			/* Initialize the database */
			try {
				createDatabase();
			} catch (DatabaseException e) {
				throw e;
			}
		}

	}
	
	private Connection connect() throws DatabaseException {
		Connection conn;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("CLASS NOT FOUND!");
			throw new DatabaseException(e.getMessage());
		}
		String dbPath = "jdbc:sqlite:" + dbfile.getAbsolutePath();
		
		try {
			conn = DriverManager.getConnection(dbPath);
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	private void createDatabase() throws DatabaseException {
		
		Connection conn = connect();
		
		ArtistsTable artists = new ArtistsTable(conn, true);
		
		SimilarArtistsTable similar = new SimilarArtistsTable(conn, true);
		
		SongsTable songs = new SongsTable(conn, true);
		
		VenuesTable venues = new VenuesTable(conn, true);
		
		EventsTable events = new EventsTable(conn, true);
		
		EventArtistMapTable eamap = new EventArtistMapTable(conn, true);
		
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}
	
	/**
	 * Get an artist from mbid
	 * @param mbid The mbid
	 * @return The artist
	 */
	public Artist getArtist(String mbid) {
		return null;
	}
	
		
	private boolean artistExists(Artist artist) throws DatabaseException {
		Connection conn = connect();
		ArtistsTable at = new ArtistsTable(conn);
		return at.contains(artist);
	}
	
	/**
	 * Adds or updates table with item
	 * @param table
	 * @param item
	 * @throws DatabaseException
	 */
	private void sync(DatabaseTable table, MusicItem item) throws DatabaseException {
		try {
			if (table.contains(item)) {
				table.update(item);
			}
			else	
				table.insert(item);
		} catch (DatabaseException e) {
			table.close();
			throw e;
		}
	}
	
	/**
	 * Updates artist in the database. If it does not exist then
	 * it will be added.
	 * @param artist The artist to sync
	 * @throws DatabaseException 
	 * @throws DatabaseException 
	 */
	public void syncArtist(Artist artist) throws DatabaseException{
		
		Connection conn = connect();
		
		ArtistsTable artists = new ArtistsTable(conn);
		
		sync(artists, artist);
		
		artists.close();
	}
	
	/**
	 * Updates song in the database. If it does not exist then
	 * it will be added
	 * @param song The song to sync
	 * @throws DatabaseException 
	 */
	public void syncSong(Song song) throws DatabaseException {
		Connection conn = connect();
		SongsTable songs = new SongsTable(conn);
		
		this.syncArtist(song.getArtist());
		
		sync(songs, song);
		
		songs.close();
	}
	
}
