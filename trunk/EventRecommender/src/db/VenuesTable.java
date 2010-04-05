package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import music.Event;
import music.MusicItem;
import music.Venue;

public class VenuesTable extends DatabaseTable {

	public VenuesTable(Connection conn) throws DatabaseException {
		super(conn);
	}

	public VenuesTable(Connection conn, boolean createTable) throws DatabaseException {
		super(conn, createTable);
	}
	
	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE venues" +
		"( " +
		"id integer NOT NULL," +
		"name VARCHAR(80) NOT NULL," +
		"city VARCHAR(80) NOT NULL," +
		"country VARCHAR(80) NOT NULL," +
		"street VARCHAR(128) NOT NULL," +
		"postalcode VARCHAR(7) NOT NULL," +
		"latitude double," +
		"longitude double," +
		"PRIMARY KEY (id)" +
		");";
		
		return SQL;
	}

	@Override
	protected PreparedStatement insertStatement() throws SQLException {
		String sql = "INSERT INTO venues " +
				"(id, name, city, country, street, postalcode, latitude, longitude) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prep = conn.prepareStatement(sql);
		return prep;
	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		String sql = "UPDATE venues SET " +
				"name = ?," +
				"city= ?," +
				"country= ?," +
				"street = ?," +
				"postalcode = ?," +
				"latitude = ?," +
				"longitude = ? " +
				"WHERE id = ?";
		PreparedStatement prep = conn.prepareStatement(sql);
		return prep;
	}
	
	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = insertStatement();
			
			Venue v = (Venue) item;
			stat.setInt(1, v.getID());
			stat.setString(2, v.getName());
			stat.setString(3, v.getCity());
			stat.setString(4, v.getCountry());
			stat.setString(5, v.getStreet());
			stat.setString(6, v.getPostalcode());
			stat.setDouble(7, v.getLatitude());
			stat.setDouble(8, v.getLongitude());
			
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
			
			Venue v = (Venue) item;
			stat.setString(1, v.getName());
			stat.setString(2, v.getCity());
			stat.setString(3, v.getCountry());
			stat.setString(4, v.getStreet());
			stat.setString(5, v.getPostalcode());
			stat.setDouble(6, v.getLatitude());
			stat.setDouble(7, v.getLongitude());
			stat.setInt(8, v.getID());
			
            int rows = stat.executeUpdate();
            return rows;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		Venue v = (Venue) item;
		try {
			PreparedStatement prep = conn.prepareStatement(
					"select count(id) from venue where id=?");
			prep.setInt(1, v.getID());

			ResultSet r = prep.executeQuery();
			int count = r.getInt(1);
			return (count > 0);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	private Venue makeVenue(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String city = rs.getString("city");
		String country = rs.getString("country");
		String street = rs.getString("street");
		String postal = rs.getString("postalcode");
		double lat = rs.getDouble("latitude");
		double lon = rs.getDouble("longitude");
		
		Venue v = new Venue(id);
		v.setCity(city);
		v.setCountry(country);
		v.setName(name);
		v.setStreet(street);
		v.setPostalcode(postal);
		v.setLatitude(lat);
		v.setLongitude(lon);
		
		return v;
	}
	
	public Venue getVenue(int id)throws DatabaseException {
		String sql = "SELECT * FROM venues " +
		"WHERE id = " + id;

		ResultSet rs;
		
		try {
			Statement select = conn.createStatement();
			rs = select.executeQuery(sql);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
			
		Venue v = null;
		try {
			while(rs.next()) {
				v = makeVenue(rs);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return v;
	}
}
