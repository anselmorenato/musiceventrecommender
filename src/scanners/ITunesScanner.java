package scanners;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class ITunesScanner {
	public ITunesScanner() {
		String file = "/Users/derek/Music/iTunes/iTunes Music Library.xml";
		try {
			Document d = getDocument(file);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document 
	getDocument(String file) throws Exception {

		// Step 1: create a DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// Step 2: create a DocumentBuilder
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(new File(file));
		return doc;
	}      
	
	public static void main(String[] args) {
		ITunesScanner isc = new ITunesScanner();
	}
}
