package net.etfbl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import net.etfbl.beans.PostBean;
import net.etfbl.beans.PostCategoryBean;
import net.etfbl.beans.UserBean;
import net.etfbl.dao.UserDAO;

@WebServlet("/Login")
public class LoginController extends HttpServlet {
	// private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/pages/login.jsp";
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		
		if ((action != null) && (action.equals("logout"))) {
			if (userBean != null)
				UserDAO.logout(userBean.getUser().getId());

			session.invalidate();
			address = "/WEB-INF/pages/login.jsp";
		}else if((userBean != null)&&(userBean.isLoggedIn())) {
			address = "/WEB-INF/pages/home.jsp";
		} else if ((action == null) || (action.equals(""))) {
			address = "/WEB-INF/pages/login.jsp";
		}else
			address = "/WEB-INF/pages/login.jsp";

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JSONObject jsonObject = new JSONObject(jsonText);
		String address = "/WEB-INF/pages/login.jsp";
		String action = jsonObject.getString("action");
		HttpSession session = request.getSession();
		
		if (action.equals("login")) {
			UserBean userBean = new UserBean();
			PostBean postBean = new PostBean();
			PostCategoryBean postCategoryBean = new PostCategoryBean();
			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");
			if (userBean.login(username, password)) {
				session.setAttribute("userBean", userBean);
				session.setAttribute("postBean", postBean);
				session.setAttribute("postCategoryBean", postCategoryBean);
			} else {
				PrintWriter pw = response.getWriter();
				pw.print("ERROR");
				pw.close();
			}
		}
	}
}
