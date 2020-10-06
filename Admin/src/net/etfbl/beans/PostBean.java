package net.etfbl.beans;
 
import java.util.List;

import javax.faces.bean.ManagedBean;

import net.etfbl.dao.EmergencyDAO;
import net.etfbl.dto.EmergencyHelp;

@ManagedBean(name = "postBean")
@javax.faces.bean.RequestScoped
public class PostBean {

	private List<EmergencyHelp> posts;
	
	public PostBean() {
		posts = EmergencyDAO.getAllEmergencyHelpPosts();
	}

	public void deletePost(Integer postId) {
		if(EmergencyDAO.deletePost(postId))
			posts.removeIf(post -> post.getId() == postId);
	}
	
	public List<EmergencyHelp> getPosts() {
		return posts;
	}

	public void setPosts(List<EmergencyHelp> posts) {
		this.posts = posts;
	}
	
	
}
