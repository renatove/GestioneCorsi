package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static Database dbInstance;
	
	private Database() {
		// private constructor
	}
	
	public static Database getInstance() {
		if(dbInstance == null) {
			dbInstance = new Database();
		}
		
		return dbInstance;
	}
	
	public Connection getConnection() throws SQLException {
		String  jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root";
		return DriverManager.getConnection(jdbcURL);
	}
}
