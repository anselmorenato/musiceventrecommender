package music;

public class Song {
	private String mbid;
	private String title;
	private Artist artist;
	private int playcount;
	
	public Song(String mbid, String title, Artist artist) {
		this.mbid = mbid;
		this.title = title;
		this.artist = artist;
		this.playcount = 0;
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
	 * @param artist the artist to set
	 */
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	/**
	 * @return the artist
	 */
	public Artist getArtist() {
		return artist;
	}
	/**
	 * @param playcount the playcount to set
	 */
	public void setPlaycount(int playcount) {
		this.playcount = playcount;
	}
	/**
	 * @return the playcount
	 */
	public int getPlaycount() {
		return playcount;
	}
	
	
}
