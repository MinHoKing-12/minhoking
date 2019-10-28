package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static void initConnection() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@192.168.0.61:1521:xe";
		String user = "hr";
		String pw = "hr";
		
		conn = DriverManager.getConnection(url, user, pw);
		
		return conn;
	}
	
}
