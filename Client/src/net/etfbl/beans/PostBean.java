package net.etfbl.beans;

import java.util.List;

import net.etfbl.dao.PostDAO;
import net.etfbl.dto.Post;

public class PostBean {

	public List<Post> getAllActiveUnemergencyPosts() {
		return PostDAO.getAllUnemergencyPosts();
	}

	public List<Post> getAllActiveEmergencyPosts() {
		return PostDAO.getAllEmergencyPosts();
	}
}
