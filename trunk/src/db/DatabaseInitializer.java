package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

	private String dbstring;
	private Connection conn;

	public DatabaseInitializer(File f) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		dbstring = "jdbc:sqlite:" + f.getName();

	}

	public void createTables() throws SQLException {
		this.conn = DriverManager.getConnection(dbstring);

		createArtistsTable();
		createSongsTable();
		createVenuesTable();
		createEventsTable();
		createSimilarArtistsTable();
		createEventArtistMapTable();

		conn.close();
	}

	private void createArtistsTable() throws SQLException {
		Statement stat = conn.createStatement();
		String SQL = "CREATE TABLE artists" +
				"( " +
				"mbid character(37) NOT NULL," +
				"name VARCHAR(80) NOT NULL," +
				"playcount integer DEFAULT 0," +
				"PRIMARY KEY (mbid)" +
				");";
		stat.execute(SQL);
	}
	
	private void createSongsTable() throws SQLException {
		Statement stat = conn.createStatement();
		String SQL = "CREATE TABLE songs" +
				"( " +
				"mbid character(37) NOT NULL," +
				"title VARCHAR(80) NOT NULL," +
				"playcount integer DEFAULT 0," +
				"artist character(37) NOT NULL," +
				"PRIMARY KEY (mbid)," +
				"FOREIGN KEY (artist) REFERENCES artists(mbid)" +
				");";
		stat.execute(SQL);
	}
	
	private void createVenuesTable() throws SQLException {
		Statement stat = conn.createStatement();
		String SQL = "CREATE TABLE venues" +
				"( " +
				"id integer NOT NULL," +
				"name VARCHAR(80) NOT NULL," +
				"city VARCHAR(80) NOT NULL," +
				"street VARCHAR(128) NOT NULL," +
				"postalcode VARCHAR(7) NOT NULL," +
				"latitude double," +
				"longitude double," +
				"PRIMARY KEY (id)" +
				");";
		stat.execute(SQL);
	}
	
	private void createEventsTable() throws SQLException {
		Statement stat = conn.createStatement();
		String SQL = "CREATE TABLE events" +
				"( " +
				"id integer NOT NULL," +
				"title VARCHAR(80) NOT NULL," +
				"venue integer NOT NULL," +
				"date text NOT NULL," +
				"decription text," +
				"website VARCHAR(80)," + 
				"ticketsite VARCHAR(80)," +
				"PRIMARY KEY (id)," +
				"FOREIGN KEY (venue) REFERENCES venues(id)" +
				");";
		stat.execute(SQL);
	}
	
	private void createSimilarArtistsTable() throws SQLException {
		Statement stat = conn.createStatement();
		String SQL = "CREATE TABLE similarartists" +
				"( " +
				"artist character(37) NOT NULL," +
				"similar character(37) NOT NULL," +
				"PRIMARY KEY (artist,similar)," +
				"FOREIGN KEY (similar) REFERENCES artists(mbid)" +
				");";
		stat.execute(SQL);
	}
	
	private void createEventArtistMapTable() throws SQLException {
		Statement stat = conn.createStatement();
		String SQL = "CREATE TABLE eventartistmap" +
				"( " +
				"event integer NOT NULL," +
				"artist character(37) NOT NULL," +
				"PRIMARY KEY (event,artist)," +
				"FOREIGN KEY (event) REFERENCES events(id)" +
				"FOREIGN KEY (artist) REFERENCES artists(mbid)" +
				");";
		stat.execute(SQL);
	}

}

