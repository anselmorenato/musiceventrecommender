package scanners;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ITunesScanner implements Scanner {
	private List<ScannerObserver> observers;
	String xmlFile;
	DefaultHandler handler;
	SAXParser saxParser;
	int count = 0;
	
	public ITunesScanner(String itunesXML) {
		xmlFile = itunesXML;
		handler = new ITunesXMLHandler(this);
		this.observers = new LinkedList<ScannerObserver>();
	
        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		}
		
	}
	
	public void scan() {
		 try {
			saxParser.parse(new File(xmlFile), handler);
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		
	}
	
	public void notifyObservers(String title, String artist, int playcount) {
		for (ScannerObserver o : this.observers) {
			o.songFound(title, artist, playcount);
		}
	}

	public void addObserver(ScannerObserver o) {
		this.observers.add(o);
	}
	
	public static void main(String[] args) {
		String file = "/Users/derek/Music/iTunes/iTunes Music Library.xml";
				
		ITunesScanner scanner = new ITunesScanner(file);
		scanner.scan();
	}
}
