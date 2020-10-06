package net.etfbl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.etfbl.dto.Comment;

public class CommentDAO {

	private static final String SQL_INSERT = "INSERT INTO comment (text, image, time, post_Id, user_Id) VALUES (?,?,?,?,?)";

	private static final String SQL_SELECT_POST_COMMENTS = "SELECT * FROM comment WHERE post_id=?";

	public static boolean insert(Comment postComment) {
		boolean result = false;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		ResultSet generatedKeys = null;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(postComment.getTime());

		Object values[] = { postComment.getText(), postComment.getImage(), currentTime, postComment.getPostId(),
				postComment.getUserId() };
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
		return result;
	}

	public static List<Comment> getAllpostComments(Integer postId) {
		List<Comment> postComments = new java.util.ArrayList<>();
		ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { postId };

		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_POST_COMMENTS, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = df.parse(rs.getString("time"));
				Comment comment = new Comment(rs.getString(2), rs.getString(3), rs.getInt(4), date, rs.getInt(6));

				postComments.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}

		return postComments;
	}

}
