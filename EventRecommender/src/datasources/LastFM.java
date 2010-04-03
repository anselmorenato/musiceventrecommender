package datasources;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import music.Artist;
import music.MusicItemException;
import music.Song;
import net.roarsoftware.lastfm.Caller;

public class LastFM {
	private String apiKey = "1cfb666ab23d365a4cdcc13044d47f33";
	private String userAgent = "tst";
	private int reqCount = 0;
	private int delay = 1100; /* milliseconds */

	public LastFM() {
		Caller.getInstance().setUserAgent(userAgent);
	}

	/**
	 * Limit to 5 requests per second to comply with TOS 
	 * averaged over 5 seconds
	 */
	public void delay() {
		if (reqCount >= 5) {
			reqCount = 0;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		reqCount++;
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

		music.Artist artist = translateArtist(lfmartist);

		// Get similar artists
		Collection<net.roarsoftware.lastfm.Artist> similar = 
			net.roarsoftware.lastfm.Artist.getSimilar(name, apiKey);

		for (net.roarsoftware.lastfm.Artist la : similar) {
			Artist a = translateArtist(la);
			if (a != null)
				artist.addSimilarArtist(a);
		}

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

	private music.Artist translateArtist(net.roarsoftware.lastfm.Artist l_artist) {
		music.Artist artist;
		try {
			artist = new music.Artist(l_artist.getMbid(), 
					l_artist.getName());
		} catch (MusicItemException e) {
			return null;
		}
		return artist;
	}

	private music.Venue translateVenue(net.roarsoftware.lastfm.Venue l_venue) {
		int id = Integer.parseInt(l_venue.getId());
		music.Venue v = new music.Venue(id);

		v.setCity(l_venue.getCity());
		v.setLatitude(l_venue.getLatitude());
		v.setLongitude(l_venue.getLongitude());
		v.setName(l_venue.getName());
		v.setPostalcode(l_venue.getPostal());
		v.setStreet(l_venue.getStreet());

		return v;
	}

	private music.Event translateEvent(net.roarsoftware.lastfm.Event l_event) {
		music.Event e = new music.Event(l_event.getId());

		String date = l_event.getStartDate().toString();
		e.setDate(date);

		e.setDescription(l_event.getDescription());

		// Ticket site
		Collection<net.roarsoftware.lastfm.Event.TicketSupplier> suppliers =
			l_event.getTicketSuppliers();

		// Get only one site
		for (net.roarsoftware.lastfm.Event.TicketSupplier ts : suppliers) {
			e.setTicketsite(ts.getWebsite());
			break;
		}


		e.setTitle(l_event.getTitle());

		music.Venue v = translateVenue(l_event.getVenue());
		e.setVenue(v);

		e.setWebsite(e.getWebsite());

		return e;
	}

	public LinkedList<music.Event> getArtistEvents(Artist a) {
		delay();

		Collection<net.roarsoftware.lastfm.Event> lfmevents = 
			net.roarsoftware.lastfm.Artist.getEvents(a.getName(), apiKey);

		LinkedList<music.Event> events = new LinkedList<music.Event>();

		for (net.roarsoftware.lastfm.Event l_event : lfmevents) {
			music.Event event = translateEvent(l_event);
			events.add(event);
		}

		return events;
	}

}

