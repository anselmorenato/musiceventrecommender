package scanners;

import java.io.File;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v1Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

import scanners.SongScanException;

public class SongScanner {
	final MP3File mp3;
	String artist;
	String title;
	String album;
	
	public SongScanner(File song) throws SongScanException {
		/*LogManager lm = LogManager.getLogManager();
		ArrayList<String> al = Collections.list(lm.getLoggerNames());
		
		for (String s : al) {
			System.out.println(s);
		}*/
			
		try {
			this.mp3 = (MP3File) AudioFileIO.read(song);
		} catch (Exception e) {
			throw new SongScanException(song.getAbsolutePath());
		}
		
		if (mp3.hasID3v2Tag()) {
			readID3V2();
		}
		else if (mp3.hasID3v1Tag()) {
			readID3V1();
		}
		else {
			artist = "";
			title = "";
		}
	}
	
	private void readID3V1() {
		Tag tag = mp3.getTag();
		
		artist = tag.getFirst(FieldKey.ARTIST);
		title = tag.getFirst(FieldKey.TITLE);
		album = tag.getFirst(FieldKey.ALBUM);
	}
	
	private void readID3V2() {
		AbstractID3v2Tag tag = (AbstractID3v2Tag) mp3.getID3v2TagAsv24();
		
		artist = tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
		title = tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
		album= tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public String getTitle() {
		return title;
	}
}
