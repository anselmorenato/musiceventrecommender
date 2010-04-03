package scanners;

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
	
	public MusicImporter(Database db, String libraryPath, String libraryMetadataPath) {
		this.db = db;
		this.libraryPath = libraryPath;
		this.metadataPath = libraryMetadataPath;
		this.lastfm = new LastFM();
	}
	
	private void scanDirectory(String path) {
		DirectoryScanner ds = new DirectoryScanner(path);
		ds.addObserver(this);
		ds.scan();
	}
	
	private void scanLibrary(String path) {
		
	}
	
	public void songFound(String songName, String artistName,int playcount) {	
		//Get artist data
		Artist artist = lastfm.lookupArtistByName(artistName);
		if (artist == null)
			return;
		
		Song song = lastfm.lookupSongByName(songName, artist);
		if (song == null)
			return;
		
		/* TODO: Use real playcount here */
		//song.setPlaycount(playcount);
		Random rand = new Random();
		int max = 200;
		int randPlaycount = rand.nextInt(max);
		song.setPlaycount(randPlaycount);
		
		artist.incrementPlaycount(playcount);
		
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
		scanDirectory(this.libraryPath);
		return true;
	}

}
