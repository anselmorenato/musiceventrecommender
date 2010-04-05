package scanners;

import java.util.List;


public class ITunesScanner implements Scanner{
	private List<ScannerObserver> observers;
	
	public ITunesScanner(String itunesXML) {
		
		
	}
	
	public void scan() {
		// TODO Auto-generated method stub
		
	}
	
	private void notifyObservers(SongScanner s) {
		for (ScannerObserver o : this.observers) {
			o.songFound(s.getTitle(), s.getArtist(), 0);
		}
	}

	public void addObserver(ScannerObserver o) {
		this.observers.add(o);
		
	}
}
