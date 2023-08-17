package hotel.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	private static Connection con;
	private DBConn() {}
	public static Connection getConnection() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "dev";
		String password = "0000";
		if(con==null){
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	public static void close(Statement stmt){
		try {
			if(stmt!=null)
				stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pstmt){
		try {
			if(pstmt!=null)
				pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt,ResultSet rs){
		try {
			if(stmt!=null)
				stmt.close();
				rs.close();	
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pstmt,ResultSet rs){
		try {
			if(pstmt!=null)
				pstmt.close();
				rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection con){
		try {
			if(con!=null)
				con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
