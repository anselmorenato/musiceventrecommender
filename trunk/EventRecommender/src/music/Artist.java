package music;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrateur
 *
 */
public class Artist implements MusicItem {
	private String mbid;
	private String name;
	private int playcount;
	List<Artist> similar;
	
	public Artist(String mbid, String name) throws MusicItemException {
				
		if (name == null || mbid == null)
			throw new MusicItemException("Artist: null parameters");
		
		if (mbid.length() == 0 || name.length() == 0)
			throw new MusicItemException("Artist: Invalid parameters");
					
		this.mbid = mbid;
		this.name = name;
		this.playcount = 0;
		this.similar = new LinkedList<Artist>();
	}

	/**
	 * @param mbid the mbid to set
	 */
	public void setMBID(String mbid) {
		this.mbid = mbid;
	}

	/**
	 * @return the mbid
	 */
	public String getMBID() {
		return mbid;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param playcount the playcount to set
	 */
	public void setPlaycount(int playcount) {
		this.playcount = playcount;
	}

	/**
	 * Increment this artist's playcount
	 * @param add The increment value
	 */
	public void incrementPlaycount(int add) {
		this.playcount += add;
	}
	
	/**
	 * @return the playcount
	 */
	public int getPlaycount() {
		return playcount;
	}
		
	/**
	 * Add a similar artist
	 * @param artist An artist that is similar
	 */
	public void addSimilarArtist(Artist artist) {
		this.similar.add(artist);
	}
	
	/**
	 * Get the list of artists which are similar
	 * @return List of similar artists
	 */
	public List<Artist> getSimilarArtists() {
		List<Artist> copy = new LinkedList<Artist>();
		copy.addAll(this.similar);
		return copy;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Artist other = (Artist) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}

}
