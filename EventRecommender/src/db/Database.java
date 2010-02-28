package db;

import java.io.File;
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
	
	private void createTable(DatabaseTable table) throws DatabaseException {
		table.create();
		table.close();
	}
	
	private void createDatabase() throws DatabaseException {
		ArtistsTable artists = new ArtistsTable(dbfile);
		createTable(artists);
		
		SimilarArtistsTable similar = new SimilarArtistsTable(dbfile);
		createTable(similar);
		
		SongsTable songs = new SongsTable(dbfile);
		createTable(songs);
		
		VenuesTable venues = new VenuesTable(dbfile);
		createTable(venues);
		
		EventsTable events = new EventsTable(dbfile);
		createTable(events);
		
		EventArtistMapTable eamap = new EventArtistMapTable(dbfile);
		createTable(eamap);
	}
	
	/**
	 * Get an artist from mbid
	 * @param mbid The mbid
	 * @return The artist
	 */
	public Artist getArtist(String mbid) {
		return null;
	}
	
		
	private boolean artistExists(Artist artist) {
		return false;
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
		
		ArtistsTable artists = new ArtistsTable(dbfile);
		
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
		SongsTable songs = new SongsTable(dbfile);
		
		this.syncArtist(song.getArtist());
		
		sync(songs, song);
		
		songs.close();
	}
	
}
