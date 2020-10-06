package net.etfbl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.etfbl.dto.Comment;
import net.etfbl.dto.Post;
import net.etfbl.dto.User;
import net.etfbl.rss.RSSReader;

public class PostDAO {

	private static final String SQL_INSERT = "INSERT INTO post (text, user_id, time, location, video, link, isEmergency) VALUES (?,?, ?,?,?,?,?)";

	private static final String SQL_SELECT_ACTIVE_UNEMERGENCY_POSTS = "SELECT * FROM post WHERE active=1 AND isEmergency=0"; // dohvatanje
																																// svih
																																// aktivnih
																																// objava
																																// i
																																// opasnosti
																																// koje
																																// nisu
																																// hitna
																																// upozorenja
																																// za
																																// prikaz
																																// po
																																// sredini
																																// stranice

	private static final String SQL_SELECT_ACTIVE_EMERGENCY_POSTS = "SELECT * FROM post WHERE active=1 AND isEmergency=1"; // dohvatanje
																															// svih
																															// aktivnih
																															// objava
																															// i
																															// opasnosti
																															// koje
																															// su
																															// hitna
																															// upozorenja
																															// za
																															// prikaz
																															// sa
																															// lijeve
																															// strane
																															// stranice

	public static Integer insert(Post post) {
		boolean result = false;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		ResultSet generatedKeys = null;
		Integer postId = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(post.getCreationTime());

		Object values[] = { post.getText(), post.getUserId(), currentTime, post.getLocation(), post.getVideo(),
				post.getLink(), post.getIsEmergency() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
				postId = generatedKeys.getInt(1);

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return postId;
	}

	public static List<Post> getAllUnemergencyPosts() {
		List<Post> activePosts = new java.util.ArrayList<>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
		User postCreator = null;
		List<String> images, categories;
		List<Comment> comments;
		Integer postId;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVE_UNEMERGENCY_POSTS);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer userId = rs.getInt("user_Id");
				postCreator = UserDAO.getById(userId);
				postId = rs.getInt("id");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = df.parse(rs.getString("time"));

				Post post = new Post(postId, rs.getString("text"), postCreator, date, rs.getString("location"),
						rs.getString("video"), rs.getString("link"));
				images = ImageDAO.getById(postId);
				post.setImages(images);
				comments = CommentDAO.getAllpostComments(postId);
				post.setCommments(comments);
				categories = PostHasPostCategory.getById(postId);
				post.setCategories(categories);
				activePosts.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		try {
			activePosts.addAll(RSSReader.parseRSSData());
			Collections.sort(activePosts, (o1, o2) -> o1.getCreationTime().compareTo(o2.getCreationTime()));
			Collections.reverse(activePosts);
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			return activePosts;
		}
	}

	public static List<Post> getAllEmergencyPosts() {
		List<Post> activePosts = new java.util.ArrayList<>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
		User postCreator = null;
		List<String> images, categories;
		List<Comment> comments;
		Integer postId;
		try {
			connection = connectionPool.checkOut();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVE_EMERGENCY_POSTS);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Integer userId = rs.getInt("user_Id");
				postCreator = UserDAO.getById(userId);
				postId = rs.getInt("id");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = df.parse(rs.getString("time"));
				Post post = new Post(postId, rs.getString("text"), postCreator, date,
						rs.getString("location"), rs.getString("video"), rs.getString("link"));
				images = ImageDAO.getById(postId);
				post.setImages(images);
				comments = CommentDAO.getAllpostComments(postId);
				post.setCommments(comments);
				categories = PostHasPostCategory.getById(postId);
				post.setCategories(categories);
				activePosts.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		Collections.sort(activePosts, (o1, o2) -> o1.getCreationTime().compareTo(o2.getCreationTime()));
		Collections.reverse(activePosts);
		return activePosts;
	}

}