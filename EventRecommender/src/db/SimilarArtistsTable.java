package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import music.Artist;
import music.Event;
import music.MusicItem;

public class SimilarArtistsTable extends DatabaseTable {

	public SimilarArtistsTable(Connection conn) throws DatabaseException {
		super(conn);
	}
	
	public SimilarArtistsTable(Connection conn, boolean createTable) throws DatabaseException {
		super(conn, createTable);
	}

	@Override
	protected String getCreateStatement() {
		String SQL = "CREATE TABLE similarartists" +
		"( " +
		"artist character(37) NOT NULL," +
		"similar character(37) NOT NULL," +
		"PRIMARY KEY (artist,similar)," +
		"FOREIGN KEY (similar) REFERENCES artists(mbid)" +
		");";

		return SQL;
	}

	@Override
	protected PreparedStatement insertStatement() throws SQLException {
		String SQL = "INSERT INTO similarartists " +
		"(artist, similar) VALUES" +
		"(?, ?)";

		PreparedStatement p = conn.prepareStatement(SQL);
		return p;
	}

	@Override
	protected PreparedStatement updateStatement() throws SQLException {
		String SQL = "UPDATE similarartists " +
		"SET similar = ?" +
		"WHERE artist = ?";

		PreparedStatement p = conn.prepareStatement(SQL);
		return p;
	}

	@Override
	public int insert(MusicItem item) throws DatabaseException {
		PreparedStatement stat;
		try {
			stat = insertStatement();

			Artist a = (Artist) item;
			int rows = 0;
			
			
			for (Artist similar : a.getSimilarArtists()) {
				if (!this.contains(a, similar)) {
					stat.setString(1, a.getMBID());
					stat.setString(2, similar.getMBID());
					rows += stat.executeUpdate();
				}
			}
				
			return rows;

		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public int update(MusicItem item) throws DatabaseException {
		/*PreparedStatement stat;
		try {
			stat = updateStatement();

			Artist a = (Artist) item;
			int rows = 0;
			
			for (Artist similar : a.getSimilarArtists()) {
				stat.setString(1, similar.getMBID());
				stat.setString(2, a.getMBID());
				rows += stat.executeUpdate();
			}
				
			return rows;

		} catch (SQLException e) {
			throw new DatabaseException(e);
		}*/
		return 0;
	}
		
	/**
	 * Get the mbids of artists similar to the given artist
	 * @param a the artist
	 * @return
	 * @throws DatabaseException 
	 */
	public List<String> getSimilar(Artist a) throws DatabaseException {
		String sql = "SELECT similar " +
				"FROM similarartists " +
				"WHERE artist = ?";
		
		
		LinkedList<String> mbids = new LinkedList<String>();
		
		ResultSet rs;
		try {
			PreparedStatement p = conn.prepareStatement(sql);
			p.setString(1, a.getMBID());
			rs = p.executeQuery();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			while(rs.next()) {
				String mbid = rs.getString("similar");
				if (mbid != null)
					mbids.add(mbid);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
		return mbids;
	}

	@Override
	public boolean contains(MusicItem item) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean contains(Artist artist, Artist similar) throws DatabaseException {
		try {
			PreparedStatement prep = conn.prepareStatement(
					"select count(*) from similarartists where artist=? AND similar=?");
			prep.setString(1, artist.getMBID());
			prep.setString(2, similar.getMBID());

			ResultSet r = prep.executeQuery();
			int count = r.getInt(1);
			return (count > 0);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
