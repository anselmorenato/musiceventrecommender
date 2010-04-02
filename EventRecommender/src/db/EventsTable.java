package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import music.Event;
import music.MusicItem;

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
