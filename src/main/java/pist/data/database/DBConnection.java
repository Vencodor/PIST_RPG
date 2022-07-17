package main.java.pist.data.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static Connection getConnection() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/PIST?useSSL=false&serverTimezone=Asia/Seoul";
			String dbID = "root";
			String dbPassword = "1324qqww";
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbURL,dbID,dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
