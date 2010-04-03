package recommendation;

import java.util.LinkedList;

import music.Artist;

import db.Database;
import db.DatabaseException;
import schedulers.Schedulable;

public class RecommendationGenerator implements Schedulable{

	LinkedList<Artist> topArtists;
	LinkedList<Artist> similarArtists;
	LinkedList<Artist> allArtists;
	
	private Database db;
	private boolean rank;
	
	/**
	 * Constructor
	 * @param data - database where the lists of artists are stored
	 * @param rank - boolean that tell us if it useful to check for the top 10 artists.
	 */
	public RecommendationGenerator(Database data,boolean rank)
	{
		db = data;
		this.rank = rank;
		similarArtists = new LinkedList<Artist>();
	}
	
	public boolean run() {
		/*TODO:
		 * - get 'top' artists
		 * - get similar ones (from db)
		 * - get events matching artists (from last.fm directly. It think separating the 'event querier' was a mistake)
		 * - store recommended events in db (possibly separate recommended events table)
		 */
		try
		{
			allArtists = db.getAllArtists();
		}
		catch (DatabaseException e) {
			return false;
		}
		
		if(rank)
		{
			try
			{
				topArtists = db.getTopArtists(10);
			}
			catch (DatabaseException e) {
				topArtists = new LinkedList<Artist>();
			}
			if(topArtists.size() > 0)
			{
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
				for(Artist art: topArtists)
				{
					if(similarArtists.contains(art))
					{
						similarArtists.remove(art);
					}
				}
			}
			
		} 
		return true;
	}

}
