package net.etfbl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {

	private static final String SQL_SELECT_BY_POST_ID = "SELECT * FROM photo WHERE post_Id=?";

	private static final String SQL_INSERT = "INSERT INTO photo (name, post_id) VALUES (?,?)";

	public static Boolean insert(List<String> images, Integer postId) {
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

		for (String image : images) {
			Object values[] = { image, postId };
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
				pstmt.executeUpdate();
				generatedKeys = pstmt.getGeneratedKeys();
				if (pstmt.getUpdateCount() > 0) {
					result = true;
				}
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				connectionPool.checkIn(connection);
			}
		}
		return result;
	}

	public static List<String> getById(int postId) {
		List<String> photos = new ArrayList<>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { postId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_POST_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String photoName = rs.getString("name");
				photos.add(photoName);
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return photos;
	}
}
