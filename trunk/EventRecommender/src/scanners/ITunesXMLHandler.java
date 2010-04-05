package scanners;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ITunesXMLHandler extends DefaultHandler {
	
	private class SongData {
		public String artist = "";
		public String name = "";
		public int playcount = -1;
		
		public boolean checkString(String str) {
			if (str == null)
				return false;
			if (str.length() < 1)
				return false;
			return true;
		}
		
		public boolean validate() {
			if (!checkString(artist))
				return false;
			if (!checkString(name))
				return false;
			if (playcount < 0)
				return false;
			return true;
		}
	}
	
	private ITunesScanner scanner;
	private StringBuilder charSB;
	private SongData song;
	String prevKey;
		
	public ITunesXMLHandler(ITunesScanner scanner) {
		this.scanner = scanner;
	}

	//===========================================================
	// SAX DocumentHandler methods
	//===========================================================
	public void startDocument() throws SAXException {
		System.out.println("START");
	}

	public void endDocument() throws SAXException {
		System.out.println("END");
	}

	public void startElement(String uri, String name, String qName, Attributes attrs) 
	throws SAXException {
		this.charSB = new StringBuilder();
		String eName = name; // element name

		if ("".equals(eName)) {
			eName = qName; // not namespaceAware
		}

		if ("dict".compareTo(eName) == 0) {
			if (this.song == null)
				this.song = new SongData();
		}
		
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name 

				if ("".equals(aName)) {
					aName = attrs.getQName(i);
				}

			}
		}
	}

	public void endElement(String uri, String name,String qName ) throws SAXException {

		String eName = name; // element name
		
		if (eName.compareTo("") == 0) {
			eName = qName;
		}
		
		if (charSB == null)
			return;
		
		if ("dict".compareTo(eName) == 0) {
			this.song = null;
			return;
		}	
		process(eName, charSB.toString());
		
		this.charSB = null;
	}
	
	private void process(String name, String data) {
		if ("key".compareTo(name) == 0) {
			this.prevKey = data;
		} else if("integer".compareTo(name) == 0) {
			gatherData(prevKey, data);
		} else if("string".compareTo(name) == 0) {
			gatherData(prevKey, data);
		}
	}
	
	private void gatherData(String key, String value) {
		if (this.song == null)
			return;
		
		if (key == null || value == null)
			return;
		
		if("Name".compareTo(key) == 0) {
			this.song.name = value;
		}
		if("Artist".compareTo(key) == 0) {
			this.song.artist = value;
		}

		if ("Play Count".compareTo(key) == 0) {
			int n = Integer.parseInt(value);
			this.song.playcount = n;
		}
		
		checkSong();
	}
	
	private void checkSong() {
		if (this.song == null)
			return;
		
		if (song.validate()) {
			scanner.notifyObservers(song.name, song.artist, song.playcount);
			this.song = null;
		}
	}
	
	public void characters(char[] buf, int offset, int len)
	throws SAXException {
		if (this.charSB == null)
			return;
		
		for (int i = 0; i < len; i++) {
			charSB.append(buf[offset + i]);
		}
	}
}