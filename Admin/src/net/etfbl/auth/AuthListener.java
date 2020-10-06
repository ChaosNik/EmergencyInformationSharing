package net.etfbl.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthListener
 */
public class AuthListener implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		String loginURI = request.getContextPath() + "/login.xhtml";
		String homeURI = request.getContextPath() + "/home.xhtml";
		
		if((loginURI.equals(request.getRequestURI().toString())) && (session != null && session.getAttribute("loggedUser") != null)) {
				response.sendRedirect(homeURI);
		}else if ((loginURI.equals(request.getRequestURI().toString())) || (session != null && session.getAttribute("loggedUser") != null)) {
			chain.doFilter(request, response); 
		}
		else {
			response.sendRedirect(loginURI);
		}
	}

}
