package scanners;

import java.util.HashMap;
import java.util.Random;

import music.Artist;
import music.Song;

import datasources.LastFM;
import db.Database;
import db.DatabaseException;

import schedulers.Schedulable;

public class MusicImporter implements ScannerObserver, Schedulable{

	private Database db;
	private String libraryPath;
	private String metadataPath;
	private LastFM lastfm;
	private HashMap<String, Artist> foundArtists;
	
	public MusicImporter(Database db, String libraryPath, String libraryMetadataPath) {
		this.db = db;
		this.libraryPath = libraryPath;
		this.metadataPath = libraryMetadataPath;
		this.lastfm = new LastFM();
		this.foundArtists = new HashMap<String, Artist>();
	}
	
	private void scanDirectory(String path) {
		DirectoryScanner ds = new DirectoryScanner(path);
		ds.addObserver(this);
		ds.scan();
	}
	
	private void scanLibrary(String path) {
		ITunesScanner its = new ITunesScanner(path);
		its.addObserver(this);
		its.scan();
	}
	
	public void songFound(String songName, String artistName, int playcount) {	
		//Get artist data
		Artist artist;
		
		if (this.foundArtists.containsKey(artistName))
			artist = this.foundArtists.get(artistName);
		else {
			artist = lastfm.lookupArtistByName(artistName);
			Artist stored = this.foundArtists.get(artist.getName());
			if (stored != null)
				artist.setPlaycount(stored.getPlaycount());
		}
		
		if (artist == null)
			return;
		
		Song song = lastfm.lookupSongByName(songName, artist);
		if (song == null)
			return;
		
		song.setPlaycount(playcount);
		/*Random rand = new Random();
		int max = 200;
		int randPlaycount = rand.nextInt(max);
		song.setPlaycount(randPlaycount);*/
		
		artist.incrementPlaycount(playcount);
		this.foundArtists.put(artist.getName(), artist);
		
		// Sync with database
		try {
			db.addSong(song);
			db.addSimilarArtists(song.getArtist());
		} catch (DatabaseException e) {
			System.err.println("Database Error: " + e.getMessage());
			return;
		}
	}

	public boolean run() {
		if(this.metadataPath.length() > 0)
			this.scanLibrary(metadataPath);
		else
			scanDirectory(this.libraryPath);
		return true;
	}

}
