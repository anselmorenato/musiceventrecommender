package schedulers;

import music.Artist;
import music.Song;

import datasources.LastFM;
import db.Database;
import db.DatabaseException;

import scanners.DirectoryScanner;
import scanners.ScannerObserver;

public class MusicImporter implements ScannerObserver{

	private Database db;
	
	public MusicImporter(Database db) {
		this.db = db;
	}
	
	public void scanDirectory(String path) {
		DirectoryScanner ds = new DirectoryScanner(path);
		ds.addObserver(this);
		ds.scan();
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

}
