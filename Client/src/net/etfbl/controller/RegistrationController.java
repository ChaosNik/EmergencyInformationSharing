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

import net.etfbl.beans.UserBean;
import net.etfbl.dto.User;

@WebServlet("/Registration")
public class RegistrationController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RegistrationController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/pages/registration.jsp";
		String action = request.getParameter("action");

		if (("registration").equals(action)) {
			address = "/WEB-INF/pages/registration.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String jsonText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		JSONObject jsonObject = new JSONObject(jsonText);
		String action = jsonObject.getString("action");
		HttpSession session = request.getSession();
		if ("registration".equals(action)) {
			checkRegistrationFields(jsonObject, request, response);
		}
	}

	private void checkRegistrationFields(JSONObject jsonObject, HttpServletRequest request,
			HttpServletResponse response) {
		String firstName = jsonObject.getString("firstName");
		String lastName = jsonObject.getString("lastName");
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		String mail = jsonObject.getString("mail");
		HttpSession session = request.getSession();
		UserBean userBean = new UserBean();
	 
		try {
			PrintWriter pw = new PrintWriter(response.getWriter());

			if (username != null) {

				if (userBean.areUsernameAndMailAllowed(username, mail, pw)) {
					User user = new User(firstName, lastName, username, password, mail);
					user.setNumberOfLogins(0);
					if (userBean.add(user)) {
						session.setAttribute("userBean", userBean);
						// dodati podatke da sacuvam, da bih mogao iskorisiti za promjenu profila
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}