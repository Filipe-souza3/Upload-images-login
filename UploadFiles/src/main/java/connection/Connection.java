package connection;

import java.sql.DriverManager;

public class Connection {
	java.sql.Connection conn = null;
	String sql ="jdbc:postgresql://localhost:5432/FileUpload?autoReconnect=true";
	
	public Connection() { }
	
	public java.sql.Connection GetConnection() {
		try {
			Class.forName("org.postgresql.Driver");	
			this.conn = DriverManager.getConnection(this.sql, "postgres", "master");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
