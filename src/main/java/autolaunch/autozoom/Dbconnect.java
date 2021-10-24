package autolaunch.autozoom;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

public class Dbconnect {

	PreparedStatement ps;
	ResultSet rs;
	Connection koneksi;
	
	public Dbconnect() {
		// TODO Auto-geznerated constructor stub
		
		try {
			koneksi = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoomdata", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	private void dbinsert(class_data data) throws SQLException {
		// TODO Auto-generated method stub
		
		ps = koneksi.prepareStatement("INSERT INTO `zoomdata` (`ID`, `class_name`, `Class_date`, `time`, `Class_URL`) VALUES (NULL, ?, ?, ?, ?) ");
		ps.setString(1, data.getClass_Name());
		ps.setString(2, data.getDate());
		ps.setString(3, data.getTime());
		ps.setString(4, data.getLink());
		ps.execute();
		
	}
	
	private ResultSet dbget() throws SQLException {
		// TODO Auto-generated method stub
		ps = koneksi.prepareStatement("Select * from zoomdata");
		rs = ps.executeQuery();
		return rs;
	}
	
	

}
