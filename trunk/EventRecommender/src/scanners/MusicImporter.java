package scanners;

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
	
	public MusicImporter(Database db, String libraryPath, String libraryMetadataPath) {
		this.db = db;
		this.libraryPath = libraryPath;
		this.metadataPath = libraryMetadataPath;
	}
	
	private void scanDirectory(String path) {
		DirectoryScanner ds = new DirectoryScanner(path);
		ds.addObserver(this);
		ds.scan();
	}
	
	private void scanLibrary(String path) {
		
	}
	
	public void songFound(String songName, String artistName,int playcount) {
		LastFM lfm = new LastFM();
		
		//Get artist data
		Artist artist = lfm.lookupArtistByName(artistName);
		if (artist == null)
			return;
		
		Song song = lfm.lookupSongByName(songName, artist);
		if (song == null)
			return;
		
		song.setPlaycount(playcount);
		
		artist.incrementPlaycount(playcount);
		
		// Sync with database
		try {
			db.syncSong(song);
		} catch (DatabaseException e) {
			System.err.println("Database Error: " + e.getMessage());
			return;
		}
	}

	public void run() {
		scanDirectory(this.libraryPath);
	}

}
