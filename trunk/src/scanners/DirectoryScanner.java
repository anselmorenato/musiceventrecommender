package scanners;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.logging.LogManager;

public class DirectoryScanner {
	
	final private ReadableDirectoryFilter dirFilter;
	final private MP3FileFilter musicFilter;
	final File root;
	
	public DirectoryScanner(String root) {
		this.root = new File(root);
		this.musicFilter = new MP3FileFilter();
		this.dirFilter = new ReadableDirectoryFilter();
	}
	
	public void scan() {
		traverse(root);
	}
	
	private void scanSong(File file) {
		try {
			SongScanner scanner = new SongScanner(file);
			System.out.println("");
			System.out.println(scanner.getArtist());
			System.out.println(scanner.getAlbum());
			System.out.println(scanner.getTitle());
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
		
		try {
			LogManager.getLogManager().readConfiguration(new StringBufferInputStream("org.jaudiotagger.level = OFF"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DirectoryScanner ds = new DirectoryScanner(home);
		ds.scan();
	}
}
