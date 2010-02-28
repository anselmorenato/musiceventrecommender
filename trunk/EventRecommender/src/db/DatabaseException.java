package db;

import java.sql.SQLException;

@SuppressWarnings("serial")
public class DatabaseException extends Exception{
	final private SQLException sql;
	
	public DatabaseException(SQLException sql) {
		super(sql.getMessage());
		this.sql = sql;
	}
	
	public DatabaseException(String msg) {
		super(msg);
		this.sql = null;
	}
	
	public boolean isSQLException() {
		return sql != null;
	}
	
	public SQLException getSQLException() {
		return sql;
	}
}
