package com.rim.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	
	public static Connection getConnection() throws Exception {
		String user ="user03";
		String password ="user03";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String driver="oracle.jdbc.driver.OracleDriver";
		
		Class.forName(driver);
				
		Connection con = DriverManager.getConnection(url, user, password);
		
		return con;
	}
		
	public static void disConnect(Connection conn) throws Exception {
		conn.close();
		
	}
	
	public static void disConnect(PreparedStatement pst, Connection conn) throws Exception {
		pst.close();
		conn.close();
		
	}
	
	public static void disConnect(ResultSet rs, PreparedStatement pst, Connection conn) throws Exception {
		rs.close();
		DBConnector.disConnect(pst, conn);
		
	}
		
		
}
