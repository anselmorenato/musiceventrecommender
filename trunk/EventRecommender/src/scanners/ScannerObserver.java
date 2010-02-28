package scanners;

public interface ScannerObserver {
	public void songFound(String song_name, String artist_name, int playcount);
}
