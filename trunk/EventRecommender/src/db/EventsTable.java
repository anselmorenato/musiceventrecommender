package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

import music.Artist;
import music.Event;
import music.MusicItem;
import music.MusicItemException;
import music.Venue;

public class EventsTable extends DatabaseTable {

	public EventsTable(Connection conn) throws DatabaseException {
		super(conn);
	}

	public EventsTable(Connection conn, boolean createTable) throws DatabaseException {
		super(conn, createTable);
	}
	
	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE events" +
		"( " +
		"id integer NOT NULL," +
		"title VARCHAR(80) NOT NULL," +
		"venue integer NOT NULL," +
		"date text NOT NULL," +
		"description text," +
		"website VARCHAR(80)," + 
		"ticketsite VARCHAR(80)," +
		"PRIMARY KEY (id)," +
		"FOREIGN KEY (venue) REFERENCES venues(id)" +
		");";
		
		return SQL;
	}

	@Override
	protected PreparedStatement insertStatement() throws SQLException {
		String sql = "INSERT INTO events " +
		"(id, title, venue, date, description, website, ticketsite) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement p = conn.prepareStatement(sql);
		
		return p;

	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		String sql = "UPDATE events SET " +
				"title = ?," +
				"venue = ?," +
				"date = ?," +
				"description = ?," +
				"website = ?," +
				"ticketsite = ? WHERE id = ?";
		
		PreparedStatement p = conn.prepareStatement(sql);
		
		return p;
	}

	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = insertStatement();
			
			Event e = (Event) item;
			
			stat.setInt(1, e.getID());
			stat.setString(2, e.getTitle());
			stat.setInt(3, e.getVenue().getID());
			stat.setString(4, e.getDate());
			stat.setString(5, e.getDescription());
			stat.setString(6, e.getWebsite());
			stat.setString(7, e.getTicketsite());
			
			int rows = stat.executeUpdate();
			return rows;
             
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	private Event makeEvent(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("title");
		int venue = rs.getInt("venue");
		String date = rs.getString("date");
		String text = rs.getString("description");
		String website = rs.getString("website");
		String ticketsite = rs.getString("ticketsite");
		
		Event ev = new Event(id);
		try 
		{
			VenuesTable ve = new VenuesTable(conn);		
			Venue v = ve.getVenue(id);
			ev.setVenue(v);
		}
		catch (DatabaseException e) {
			return null;
		}
		ev.setTitle(name);
		ev.setDate(date);
		ev.setDescription(text);
		ev.setTicketsite(ticketsite);
		ev.setWebsite(website);
		
		return ev;
	}
	
	public LinkedList<Event> getAllEvents() throws DatabaseException {
		String sql = "SELECT * FROM events " +
				"ORDER BY date ";
		
		ResultSet rs;
		LinkedList<Event> all = new LinkedList<Event>();
		
		try {
			Statement select = conn.createStatement();
			rs = select.executeQuery(sql);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			while(rs.next()) {
				Event ev = makeEvent(rs);
				if (ev != null)
					all.add(ev);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return all;
	}
	
	public Event getEvent(int id) throws DatabaseException {
		String sql = "SELECT * FROM artists " +
				"WHERE id = " + id;
		
		ResultSet rs;
		
		
		try {
			Statement select = conn.createStatement();
			rs = select.executeQuery(sql);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		Event a = null;
		try {
			while(rs.next()) {
				a = makeEvent(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return a;
	}


	@Override
	public int update(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = updateStatement();
			Event e = (Event) item;
			
			stat.setString(1, e.getTitle());
			stat.setInt(2, e.getVenue().getID());
			stat.setString(3, e.getDate());
			stat.setString(4, e.getDescription());
			stat.setString(5, e.getWebsite());
			stat.setString(6, e.getTicketsite());
			stat.setInt(7, e.getID());
			
			int rows = stat.executeUpdate();
			return rows;
             
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}
}
