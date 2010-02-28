package datasources;

import music.MusicItemException;
import music.Song;
import net.roarsoftware.lastfm.Caller;

public class LastFM {
	private String apiKey = "1cfb666ab23d365a4cdcc13044d47f33";
	private String userAgent = "tst";
		
	public LastFM() {
		Caller.getInstance().setUserAgent(userAgent);
	}
	
	/**
	 * Limit to 5 requests per second to comply with TOS 
	 * averaged over 5 seconds
	 */
	public void delay() {
		
	}
	
	/**
	 * Lookup artist by name
	 * @param name name of the artist
	 * @return Artist or null if not found
	 */
	public music.Artist lookupArtistByName(String name) {
		delay();
		
		net.roarsoftware.lastfm.Artist lfmartist = 
			net.roarsoftware.lastfm.Artist.getInfo(name, apiKey);
		
		if(lfmartist == null)
			return null;
		
		music.Artist artist;
		try {
			artist = new music.Artist(lfmartist.getMbid(), 
					lfmartist.getName());
		} catch (MusicItemException e) {
			return null;
		}
		
		artist.setPlaycount(0);
		
		return artist;
	}
	
	/**
	 * Lookup song by name
	 * @param name the song name
	 * @param artist the song's artist
	 * @return Song or null if not found
	 */
	public music.Song lookupSongByName(String name, music.Artist artist) {
		
		delay();
		
		net.roarsoftware.lastfm.Track track = 
			net.roarsoftware.lastfm.Track.getInfo(artist.getName(), name, apiKey);
		
		if(track == null)
			return null;
		
		
		Song song;
		try {
			song = new Song(track.getName(), track.getAlbum(), artist);
		} catch (MusicItemException e) {
			return null;
		}
		return song;
	}
}
