package net.etfbl.dao;

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

public class EmergencyHelpDAO {

	private static final String SQL_INSERT = "INSERT INTO emergencyHelp (name, time, location, description, image, category) VALUES (?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE_POST_STATE = "UPDATE emergencyHelp SET active = 0 WHERE id=?";
	
	private static final String SQL_SELECT_EMERGENCY_POSTS = "SELECT * FROM emergencyHelp";
	
	public static Boolean insert(EmergencyHelp emergencyHelp) {
		Boolean result = false;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		ResultSet generatedKeys = null;
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
	
	public static Boolean deletePost(Integer postId) {
		boolean result = false;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_POST_STATE);
			pstmt.setInt(1, postId);
			pstmt.executeUpdate();
			if (pstmt.getUpdateCount() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}

	public static List<EmergencyHelp> getAll() {
		List<EmergencyHelp> activePosts = new java.util.ArrayList<EmergencyHelp>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
	
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_EMERGENCY_POSTS);
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
}