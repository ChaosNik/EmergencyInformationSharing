package net.etfbl.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.etfbl.dao.UserDAO;
import net.etfbl.dto.User;

@ManagedBean(name = "userBean")
@javax.faces.bean.RequestScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	public ArrayList<Integer> numberOfUsersPerHour;
							   
	private int numberOfActiveUsers;
	
	private int numberOfRegisteredUsers;

	private ArrayList<User> users = new ArrayList<User>();
	
	private User selectedUser = null;

	private String errorMsg;
	
	private String selectedPage;
	
	public UserBean() {
		super();
		user = new User();
		numberOfUsersPerHour = new ArrayList<Integer>();
		getNumberOfActiveAndRegistredUsers();
		users = UserDAO.getRegisteredAndUncofirmedUsers();
		getNumberOfLoginsPerHour();
	}
	public String login() {
		if ((user = UserDAO.getUserByUsernameAndPasswordAndActive(user.getUsername(), user.getPassword(), 1)) != null) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("loggedUser", user);
			return "home.xhtml?faces-redirect=true";
		}
		else {
			this.setErrorMsg("Greska");
		}
		return null;
	}

	private void getNumberOfActiveAndRegistredUsers() {
		numberOfActiveUsers = UserDAO.getNumberOfActiveUsers();
		numberOfRegisteredUsers = UserDAO.getNumberOfRegisteredUsers();
	}

	private void getNumberOfLoginsPerHour() {
		for (int i = 1; i <= 24; i++) {
			Calendar dateFrom = Calendar.getInstance();
			Calendar dateTo = Calendar.getInstance();
			dateFrom.add(Calendar.HOUR, -i);
			dateTo.add(Calendar.HOUR, (-i + 1));
			int number = UserDAO.getNumberOfLoggedUsersBetweenTime(new Date(dateFrom.getTimeInMillis()),
					new Date(dateTo.getTimeInMillis()));
			numberOfUsersPerHour.add(number);
		}
	}

	public ArrayList<Integer> getNumberOfUsersPerHour() {
		return numberOfUsersPerHour;
	}
	
	public List<User> getUsers(){
		return users;
	}

	public String approveUser(Integer id) {
		UserDAO.updateUserState(id, 1);
		for(User user : users) {
		    if(user.getId() == id) {
		        user.setActive(1);
		    }
		}
		return null;
	}
	
	public String rejectUser(Integer id) {
		UserDAO.rejectUser(id, 0);
		users.removeIf(user -> user.getId() == id);
		return null;
	}
	
	public String blockUser(Integer id) {
		UserDAO.updateUserState(id, 0);
		users.removeIf(user -> user.getId() == id);
		return null;
	}
	
	public String resetPassword(Integer id) {
		UserDAO.updatePassword(id);
		return null;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getNumberOfActiveUsers() {
		return numberOfActiveUsers;
	}

	public void setNumberOfActiveUsers(int numberOfActiveUsers) {
		this.numberOfActiveUsers = numberOfActiveUsers;
	}

	public int getNumberOfRegisteredUsers() {
		return numberOfRegisteredUsers;
	}

	public void setNumberOfRegisteredUsers(int numberOfRegisteredUsers) {
		this.numberOfRegisteredUsers = numberOfRegisteredUsers;
	}

	
	public void setNumberOfUsersPerHour(ArrayList<Integer> numberOfUsersPerHour) {
		this.numberOfUsersPerHour = numberOfUsersPerHour;
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}
	
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSelectedPage() {
		return selectedPage;
	}
	public void setSelectedPage(String selectedPage) {
		this.selectedPage = selectedPage;
	}	
}
