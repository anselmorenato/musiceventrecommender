package GUI;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import music.Artist;
import music.Event;
import music.Venue;
import db.Database;
import db.DatabaseException;

public class ListPanel extends JPanel{
	
    private JTextArea textArea;
    private LinkedList<String> ls;
	private Database data;
	
	public JPanel panel; 
	
	public ListPanel(Database data)
	{
        ls = new LinkedList<String>();
		this.data = data;
		
		initialization();
	}
	
	public void initialization()
	{
		String ev = getEvents();
		if(ev == null){
			ev = "Music events not found!";
		}
		textArea = new JTextArea(ev);
		textArea.setEditable(false);
        textArea.setColumns(50);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);


		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		panel = new JPanel(new BorderLayout());
		panel.add(textArea);
		panel.setVisible(true);
		
		
	}
	
	public String getEvents()
	{
		LinkedList<Event> events;
		
		try
		{
		events = data.getAllEvents();
		}
		catch (DatabaseException e) {
			return null;
		}
		
		String date = "";
		String cell = "";
		for(Event e: events)
		{
			if(!date.equals(e.getDate()))
			{
				if(cell.length()!= 0)
				{
					ls.add(cell);
				}
				date = e.getDate();
				cell += date + "/n";
			}
			cell += "/n" + e.getTitle() + "/n" + "Artist(s): ";
			for(Artist art: e.getArtists())
			{
				cell = cell + art.getName();
			}
			cell += "/nVenue:/n";
			Venue v = e.getVenue();
			cell += v.getName() + "/n" +
			v.getStreet() + "/n" + v.getPostalcode() + "/n" +
			v.getCity() + ", " + v.getCountry() +"/n";
			cell += "Latitude : " + v.getLatitude();
			cell += "/nLongitude: " + v.getLongitude();
			cell += "/nDescription: " + e.getDescription();
			cell += "/nTicket site: " + e.getTicketsite();
			cell += "/nWebsite: " +e.getWebsite() + "/n";	
		}
		return cell;
	}
	
}
