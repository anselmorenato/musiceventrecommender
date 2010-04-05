package scanners;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.LogManager;

@SuppressWarnings("deprecation")
public class DirectoryScanner implements Scanner{
	
	final private ReadableDirectoryFilter dirFilter;
	final private MP3FileFilter musicFilter;
	final File root;
	private List<ScannerObserver> observers;
	
	public DirectoryScanner(String root) {
		this.root = new File(root);
		this.musicFilter = new MP3FileFilter();
		this.dirFilter = new ReadableDirectoryFilter();
		this.observers = new LinkedList<ScannerObserver>();
		
		/* Reduce jaudiotagger verbosity */
		try {
			LogManager.getLogManager().readConfiguration(new StringBufferInputStream("org.jaudiotagger.level = OFF"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scan() {
		traverse(root);
	}
	
	private void notifyObservers(SongScanner s) {
		for (ScannerObserver o : this.observers) {
			o.songFound(s.getTitle(), s.getArtist(), 0);
		}
	}
	
	private void scanSong(File file) {
		try {
			SongScanner scanner = new SongScanner(file);
			System.out.println("");
			System.out.println(scanner.getArtist());
			System.out.println(scanner.getAlbum());
			System.out.println(scanner.getTitle());
			notifyObservers(scanner);
			
		} catch (SongScanException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void traverse(File dir) {
		
		File[] dirs = dir.listFiles(dirFilter);
		File[] contents = dir.listFiles(musicFilter);
		
		for (File f : contents) {
			if (f != null)
				scanSong(f);
		}
		
		for (File d : dirs)
			traverse(d);
	}
	
	public static void main(String[] args) {
		String home = "/Users/derek/Music";
		//String home = "D:/Music/";
		
		
		DirectoryScanner ds = new DirectoryScanner(home);
		ds.scan();
	}

	public void addObserver(ScannerObserver o) {
		this.observers.add(o);
	}
}
