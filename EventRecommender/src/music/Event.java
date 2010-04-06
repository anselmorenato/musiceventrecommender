package music;

import java.util.LinkedList;
import java.util.List;

public class Event implements MusicItem {
	final private int id;
	private String title;
	private String date;
	private String description;
	private String website;
	private String ticketsite;
	private Venue venue;
	private List<Artist> artists;
	
	public Event(int id) {
		this.id = id;
		this.artists = new LinkedList<Artist>();
	}
	
	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param ticketsite the ticketsite to set
	 */
	public void setTicketsite(String ticketsite) {
		this.ticketsite = ticketsite;
	}
	/**
	 * @return the ticketsite
	 */
	public String getTicketsite() {
		return ticketsite;
	}
	
	/**
	 * Add an artist to this event
	 * @param artist The artist to add to this event
	 */
	public void addArtist(Artist artist) {
		this.artists.add(artist);
	}

	/**
	 * Get the list of artists performing at this event
	 * @return List of artists
	 */
	public List<Artist> getArtists() {
		List<Artist> copy = new LinkedList<Artist>();
		copy.addAll(this.artists);
		return copy;
	}
	
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	/**
	 * @return the venue
	 */
	public Venue getVenue() {
		return venue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artists == null) ? 0 : artists.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((venue == null) ? 0 : venue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (date == null) {
			if (other.getDate() != null)
				return false;
		} else if (!date.equals(other.getDate()))
			return false;
		if (title == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!title.equals(other.getTitle()))
			return false;
		return true;
	}
	
	
}
