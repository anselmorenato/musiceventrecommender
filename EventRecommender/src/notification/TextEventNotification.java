package notification;

import java.util.List;

import music.Artist;
import music.Event;

public class TextEventNotification {
	Event event;
	String msg;
	
	public TextEventNotification(Event event) {
		this.event = event;
		generateMessage();
	}
	
	private void generateMessage() {
		String header = event.getTitle() + "\n";
		String desc = event.getDescription() + "\n";
		String date = "Date: " + event.getDate() + "\n";
		String web = "Website: " + event.getWebsite() + "\n";
		String tickets = "Tickets: " + event.getTicketsite() + "\n";
		String artists = "Participating Artists: ";
		
		List<Artist> artistList = event.getArtists();
		for (Artist a: artistList) {
			artists += a.getName() + ", ";
		}
		
		if (artistList.size() > 0)
			artists = artists.substring(0, artists.length() - 2);
		
		this.msg = header + artists + desc + date + web + tickets;
	}
	
	public String getText() {
		return msg;
	}
	
	public String toString() {
		return msg;
	}
}
