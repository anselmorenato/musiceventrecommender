package music;

public class Song implements MusicItem {
	private String title;
	private String album;
	private Artist artist;
	private int playcount;
	
	public Song(String title, String album, Artist artist) throws MusicItemException {
			
		if (title == null || album == null || artist == null)
			throw new MusicItemException("Song: null parameters");
		
		if (title.length() == 0 || album.length() == 0)
			throw new MusicItemException("Song: Invalid parameters");
			
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.playcount = 0;
		
		System.out.println("Album = " + album);
		System.out.println("Artist = " + artist.getMBID());
		System.out.println("title = " + title);
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

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}
	
	
}
