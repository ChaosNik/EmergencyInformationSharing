package net.etfbl.dao;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.etfbl.dto.EmergencyHelp;
import net.etfbl.dto.User;

public class EmergencyDAO {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	  
	private static final String SQL_INSERT = "INSERT INTO emergencyHelp (name, time, location, description, image, category) VALUES (?,?,?,?,?,?)";
	
	private static final String SQL_SELECT_ACTIVE_EMERGENCY_POSTS = "SELECT * FROM emergencyHelp WHERE active=1";
	
	public static Boolean insert(EmergencyHelp emergencyHelp) {
		Boolean result = false;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		ResultSet generatedKeys = null;
		Integer postId = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(emergencyHelp.getTime());
		
		Object values[] = { emergencyHelp.getName(), currentTime, emergencyHelp.getLocation(), emergencyHelp.getDescription(), emergencyHelp.getImage(), emergencyHelp.getCategory()};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
				result = true;
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}

	public static List<EmergencyHelp> getAllEmergencyHelpPosts(){
		List<EmergencyHelp> activePosts = new java.util.ArrayList<EmergencyHelp>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
	
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVE_EMERGENCY_POSTS);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				EmergencyHelp post = new EmergencyHelp(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("time"), rs.getString("location"), rs.getString("description"), rs.getString("image"), rs.getString("category"));  
				activePosts.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}

		return activePosts;
	}

	public static Boolean deletePost(Integer postId) {
			Integer responseCode = -1;
			try {

				URL url = new URL("http://localhost:8080/Help/api/emergencyHelp/delete/" + postId);

				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestMethod("DELETE");
				connection.setRequestProperty("charset", "utf-8");
				connection.connect();

				responseCode = connection.getResponseCode();
				connection.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (responseCode != 200)
				return false;
			return true;
		}
}