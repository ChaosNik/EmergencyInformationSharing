package net.etfbl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import net.etfbl.dto.User; 

public class UserDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM admin WHERE username=? AND password=?";
		
	private static final String SQL_SELECT_ACTIVE_AND_UNCONFIRMED_USERS = "SELECT * FROM user where (active = 1) OR (active = 0 AND loginTime is null) ";
	
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM user WHERE id=?";
	
	private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE user SET password=? WHERE id=?";

	private static final String SQL_UPDATE_USER_STATE = "UPDATE user SET active =?, logintime=? WHERE id=?";

	private static final String SQL_COUNT_NUMBER_OF_ACTIVE_USERS = "SELECT COUNT(*) FROM user WHERE isLogedIn = 1";
	
	private static final String COUNT_LOGGED_USERS_BETWEEN_TIME = "SELECT COUNT(*) as number FROM user u INNER JOIN user_logged ul ON (u.id=ul.user_id) WHERE loggedInDate BETWEEN ? AND ?";

	private static final String SQL_COUNT_NUMBER_OF_REGISTRED_USERS = "SELECT COUNT(*) FROM user WHERE active = 1";

	private static final String SQL_REJECT_USER = "UPDATE user SET active =?, loginTime=?, logoutTime=? WHERE id=?";

	public static User getUserByUsernameAndPasswordAndActive(String username, String password, Integer active){
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username, password};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				user = new User(rs.getInt("id"),rs.getString("username"), rs.getString("password"));
			}
																														
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		} 
		return user;
	}

	public static ArrayList<User> getRegisteredAndUncofirmedUsers() {
		ArrayList<User> result = new ArrayList<User>();
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = connection.prepareStatement(SQL_SELECT_ACTIVE_AND_UNCONFIRMED_USERS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new User(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("username"), rs.getString("password"), rs.getString("mail"),
						rs.getString("photo"), rs.getString("country"), rs.getString("region"), rs.getString("city"),
						rs.getInt("notificationonmail"), rs.getInt("notificationinapp"), rs.getInt("numberoflogins"), rs.getInt("active")));
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}

	public static boolean updatePassword(Integer userId) {
		boolean result = false;
		Connection connection = null;
		Random rand = new Random();
		String randomString = "" + rand.nextInt(90000000) + 10000000;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD);
			pstmt.setString(1, randomString);
			pstmt.setInt(2, userId);
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

	public static boolean updateUserState(Integer userId, Integer active) {
		boolean result = false;
		Connection connection = null;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String currentDateTime = format.format(date);
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_USER_STATE);
			pstmt.setInt(1,	active);
			pstmt.setString(2,	currentDateTime);
			pstmt.setInt(3, userId);
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

	public static User getById(int postId) {
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {postId};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_ID, false, values);
			rs = pstmt.executeQuery();
			if (rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setphoto(rs.getString("photo"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
	  		}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		} 
		return user;
	}

	public static Integer getNumberOfActiveUsers() {
		Integer numberOfActiveUsers = 0;
		Connection connection = null;
		ResultSet rs = null;
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_COUNT_NUMBER_OF_ACTIVE_USERS, false);
			rs = pstmt.executeQuery();
			if (rs.next()){
			 numberOfActiveUsers = rs.getInt("count(*)");	
			}
																														
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		} 
		return numberOfActiveUsers;
	}

	public static Integer getNumberOfRegisteredUsers() {
		Integer numberOfRegistredUsers = 0;
		Connection connection = null;
		ResultSet rs = null;
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_COUNT_NUMBER_OF_REGISTRED_USERS, false);
			rs = pstmt.executeQuery();
			if (rs.next()){
				numberOfRegistredUsers = rs.getInt("count(*)");	
			}
																														
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		} 
		return numberOfRegistredUsers;
	}

	public static void rejectUser(Integer userId, int active) {
		Connection connection = null;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String currentDateTime = format.format(date);
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = connection.prepareStatement(SQL_REJECT_USER);
			pstmt.setInt(1,	active);
			pstmt.setString(2,	currentDateTime);
			pstmt.setString(3,	currentDateTime);
			pstmt.setInt(4, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
	}
	
	public static int getNumberOfLoggedUsersBetweenTime(Date dateFrom, Date dateTo) {
		int result = 0;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = connection.prepareStatement(COUNT_LOGGED_USERS_BETWEEN_TIME);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			pstmt.setString(1, dateFormat.format(dateFrom));
			pstmt.setString(2, dateFormat.format(dateTo));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt("number");
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return result;
	}
}
