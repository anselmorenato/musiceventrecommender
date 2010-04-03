package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import music.Artist;
import music.Event;
import music.MusicItem;
import music.Song;
import music.Venue;

public class Database {
	final private File dbfile;
	
	/**
	 * Constructor
	 * @param path - path to the SQLite database
	 * @throws DatabaseException
	 */
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
	
	/**
	 * Connect to the database
	 * @return An sql conncetion
	 * @throws DatabaseException
	 */
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
			return conn;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	/**
	 * Create all database tables
	 * @throws DatabaseException
	 */
	private void createDatabase() throws DatabaseException {
		
		Connection conn = connect();
		
		/* Make this a transaction */
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
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
	
	/**
	 * Check if the given artist exists in the database
	 * @param artist
	 * @return true or false
	 * @throws DatabaseException
	 */
	private boolean artistExists(Artist artist) throws DatabaseException {
		Connection conn = connect();
		ArtistsTable at = new ArtistsTable(conn);
		boolean result = at.contains(artist);
		at.closeConnection();
		return result;
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
			table.closeConnection();
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
	private void store(Artist artist) throws DatabaseException{
		
		Connection conn = connect();
		
		ArtistsTable artists = new ArtistsTable(conn);
		
		sync(artists, artist);
		
		artists.closeConnection();
	}
	
	/**
	 * Add a venue to the database
	 * @param venue - the venue
	 * @throws DatabaseException 
	 */
	private void store(Venue venue) throws DatabaseException {
		Connection conn = connect();
		
		VenuesTable venues = new VenuesTable(conn);
		
		sync(venues, venue);
		
		venues.closeConnection();
	}
	
	/**
	 * Updates song in the database. If it does not exist then
	 * it will be added
	 * @param song The song to sync
	 * @throws DatabaseException 
	 */
	public void addSong(Song song) throws DatabaseException {
		Connection conn = connect();
		SongsTable songsTable = new SongsTable(conn);
		
		this.store(song.getArtist());
		
		sync(songsTable, song);
		
		songsTable.closeConnection();
	}
	
	/**
	 * Add an event to the database
	 * @param event - the event
	 * @throws DatabaseException 
	 */
	public void addEvent(Event event) throws DatabaseException {
		Connection conn = connect();
		
		EventsTable events = new EventsTable(conn);
		
		sync(events, event);
		
		EventArtistMapTable map = new EventArtistMapTable(conn);
		
		map.insert(event);
		
		events.closeConnection();
	}
	
	public void addSimilarArtists(Artist artist) throws DatabaseException {
		Connection conn = connect();
		
		SimilarArtistsTable simTable = new SimilarArtistsTable(conn);
				
		simTable.insert(artist);
		
		simTable.closeConnection();
	}
	
	/**
	 * Get a list of 'top' artists
	 * @param count - Maximum number of artists to return
	 * @return A list of at most count of the top artists
	 * @throws DatabaseException
	 */
	public List<Artist> getTopArtists(int count) throws DatabaseException {
		if (count <= 0)
			return new LinkedList<Artist>();
		
		Connection conn = connect();
		ArtistsTable at = new ArtistsTable(conn);
		
		return at.getMostPlayedArtists(count);
	}
	
	/**
	 * Get a list of artists similar to the given one
	 * @param theArtist - the artist
	 * @return A list of similar artists
	 * @throws DatabaseException
	 */
	public List<Artist> getSimilarArtists(Artist theArtist) throws DatabaseException {
		Connection conn = connect();
		LinkedList<Artist> similar = new LinkedList<Artist>();
		
		
		SimilarArtistsTable simTable = new SimilarArtistsTable(conn);
		ArtistsTable artTable = new ArtistsTable(conn);
		
		List<String> mbids = simTable.getSimilar(theArtist);
		for (String mbid : mbids) {
			Artist a = artTable.getArtist(mbid);
			if (a != null)
				similar.add(a);
		}
		
		return similar;
	}
		
}
