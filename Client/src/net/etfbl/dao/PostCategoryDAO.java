package net.etfbl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.etfbl.dto.PostCategory;

public class PostCategoryDAO {

	private static final String SQL_SELECT_ACTIVE_CATEGORIES = "SELECT * FROM postType WHERE active=1";

	public static List<PostCategory> getAllActiveCategories() {
		List<PostCategory> activeCategories = new java.util.ArrayList<>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVE_CATEGORIES);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				PostCategory category = new PostCategory();
				category.setId(resultSet.getInt(1));
				category.setName(resultSet.getString(2));
				activeCategories.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}

		return activeCategories;
	}
}
