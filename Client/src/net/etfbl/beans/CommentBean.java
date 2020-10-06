package net.etfbl.beans;

import net.etfbl.dao.CommentDAO;
import net.etfbl.dto.Comment;

public class CommentBean {

	public void insertComment(String description, String image, int postId) {
		CommentDAO.insert(new Comment(description, image, postId));
	}

}
