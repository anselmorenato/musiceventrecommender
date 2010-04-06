package GUI;

import java.awt.BorderLayout;
import java.util.LinkedList;

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
	private Database data;
	
	public JPanel panel; 
	
	public ListPanel(Database data)
	{
		this.data = data;
		
		initialization();
	}
	
	public void initialization()
	{
		textArea = new JTextArea();
		textArea.setEditable(false);
        textArea.setColumns(30);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        getEvents();

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		panel = new JPanel(new BorderLayout());
		panel.add(scrollPane);
		panel.setVisible(true);
		
		
	}
	
	public void getEvents()
	{
		LinkedList<Event> events;
		
		try
		{
		events = data.getAllEvents();
		}
		catch (DatabaseException e) {
			return;
		}
		
		String date = "";
		for(Event e: events)
		{
			if(!date.equals(e.getDate()))
			{
				date = e.getDate();
				textArea.append("\n" +date + "\n");
			}
			textArea.append("\nTitle: " + e.getTitle() + "\n\n" + "Artist(s): \n");
			for(Artist art: e.getArtists())
			{
				textArea.append(art.getName()+"\n");
			}
			Venue v = e.getVenue();
			if(v != null){
			textArea.append("\nVenue:\n");
			textArea.append(v.getName() + "\n" +
			v.getStreet() + "\n" + v.getPostalcode() + "\n" +
			v.getCity() + ", " + v.getCountry() +"\n");
			textArea.append("Latitude : " + v.getLatitude());
			textArea.append("\nLongitude: " + v.getLongitude());
			}
			textArea.append("\nDescription: " + e.getDescription());
			textArea.append("\nTicket site: " + e.getTicketsite());
			textArea.append("\nWebsite: " +e.getWebsite() + "\n");	
		}
	}
	
}
