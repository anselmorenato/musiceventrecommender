package recommendation;

import java.util.LinkedList;

import music.Artist;
import music.Event;
import music.Location;

import datasources.LastFM;
import db.Database;
import db.DatabaseException;
import schedulers.Schedulable;

public class RecommendationGenerator implements Schedulable{

	private LinkedList<Artist> topArtists;
	private LinkedList<Artist> similarArtists;
	private LinkedList<Artist> allArtists;
	private LinkedList<Event> recommendations;
	private Location local;
	private LastFM lastfm;

	private Database db;
	private int rank;
	private boolean all;

	/**
	 * Constructor
	 * @param data - database where the lists of artists are stored
	 * @param rank - int to check for the top # artists.
	 */
	public RecommendationGenerator(Database data,Location location,boolean all, int rank)
	{
		db = data;
		this.rank = rank;
		this.all = all;
		similarArtists = new LinkedList<Artist>();
		recommendations = new LinkedList<Event>();
		local = location;
		this.lastfm = new LastFM();
	}

	public boolean run() {
		if(all){
			try
			{
				allArtists = db.getAllArtists();
			}
			catch (DatabaseException e) {
				return false;
			}
			if(allArtists.size() <= 0) return false;
		}
	
		if(rank > 0)
		{
			//Get the top 10 artists
			try
			{
				topArtists = db.getTopArtists(rank);
			}
			catch (DatabaseException e) {
				topArtists = new LinkedList<Artist>();
			}
			if(topArtists.size() > 0)
			{/*
				// Get all similar artists for the top 10 artists
				LinkedList<Artist> tmp;
				
				for(Artist art: topArtists)
				{
					try
					{
						tmp = db.getSimilarArtists(art);
					}
					catch (DatabaseException e) {
						tmp = null;
					}
					if(tmp != null)
					{
						similarArtists.addAll(tmp);
					}
				}
				//Removes artists that repeat themselves in the three lists
				for(Artist art: topArtists)
				{
					if(similarArtists.contains(art))
					{
						similarArtists.remove(art);
					}
					if(all)allArtists.remove(art);
				}	
				for(Artist arty: similarArtists)
				{
					if(all && allArtists.contains(arty)) 
						allArtists.remove(arty);	
				}*/
				//Get a list of events
				for(Artist art: topArtists)
				{
					LinkedList<Event> artEvent= lastfm.getArtistEvents(art);
					for(Event e: artEvent)
					{
						if(!recommendations.contains(e)){
								recommendations.add(e);
								int i = recommendations.indexOf(e);
								recommendations.get(i).addArtist(art);
						}
						else
						{
							int i = recommendations.indexOf(e);
							recommendations.get(i).addArtist(art);
						}
					}
				}
				/*
				for(Artist arty: similarArtists)
				{
					LinkedList<Event> artEvent= lastfm.getArtistEvents(arty);
					for(Event e: artEvent)
					{
							if(local.compareByCity(e.getVenue().getCity())
									&& local.compareByCountry(e.getVenue().getCountry()))
								recommendations.add(e);
					}
				}
				*/
			}
		}
		// get the events of the remaining artists.
		if(all)
		{
			for(Artist anArtist: allArtists)
			{
				LinkedList<Event> artEvent= lastfm.getArtistEvents(anArtist);
				for(Event e: artEvent)
				{
					if(!recommendations.contains(e)){
							recommendations.add(e);
							int i = recommendations.indexOf(e);
							recommendations.get(i).addArtist(anArtist);
					}
					else
					{
						int i = recommendations.indexOf(e);
						recommendations.get(i).addArtist(anArtist);
					}
				}
			}
		}
		for(Event e: recommendations)
		{
			try {
				db.addEvent(e);
			} catch (DatabaseException e1) {
				e1.printStackTrace();
			}
		}
		return true;
	}
	
	public static void main(String [] args)
	{
		Database db;
		try {
			db = new Database("musiceventdata.db");
		} catch (DatabaseException e) {
			e.printStackTrace();
			return;
		}
		Location loc = new Location("Montreal","Canada");
		RecommendationGenerator rg = new RecommendationGenerator(db,loc,true,5);
		rg.run();
		System.out.println("Done!");
	}

}
