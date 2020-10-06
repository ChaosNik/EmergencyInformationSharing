package net.etfbl.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import net.etfbl.beans.UserBean;
import net.etfbl.dao.CommentDAO;
import net.etfbl.dto.Comment;

@WebServlet("/Comment")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CommentController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		if(userBean != null && userBean.isLoggedIn()) {
			String jsonText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			JSONObject jsonObject = new JSONObject(jsonText);
			createComment(jsonObject, request);
		}
	}

	private void createComment(JSONObject jsonObject, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		String description = null, image = null;
		Integer postId = jsonObject.getInt("postId");
		Integer userId = jsonObject.getInt("userId");

		if (!jsonObject.isNull("description")) {
			description = jsonObject.getString("description");
		}
		if (!jsonObject.isNull("image")) {
			image = jsonObject.getString("image");
		}

		java.util.Date dt = new java.util.Date();
		Comment postComment = new Comment(description, image, postId, dt, userId);
		CommentDAO.insert(postComment);
	}

}
