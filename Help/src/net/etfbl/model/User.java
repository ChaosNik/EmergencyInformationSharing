package net.etfbl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class User {
	private String username;
	private String hash;
	private LocalDateTime loginTime;
	private LocalDateTime logoutTime;
	private boolean isActive;

	public User(String username, String hash, LocalDateTime loginTime) {
		this.username = username;
		this.hash = hash;
		this.loginTime = loginTime;
		this.logoutTime = null;
		isActive = true;
	}

	public User() {
	}

	public User(String username, String hash, LocalDateTime loginTime, LocalDateTime logoutTime, boolean isActive) {
		super();
		this.username = username;
		this.hash = hash;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public LocalDateTime getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(LocalDateTime logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashMap<String, String> toMap() {
		HashMap<String, String> obj = new HashMap<>();
		obj.put("username", username);
		obj.put("hashPassword", hash);
		if (loginTime != null)
			obj.put("loginTime", loginTime.toString());
		else
			obj.put("loginTime", "");

		if (logoutTime != null)
			obj.put("logoutTime", logoutTime.toString());
		else
			obj.put("logoutTime", "");
		if (isActive)
			obj.put("isActive", "true");
		else
			obj.put("isActive", "false");
		return obj;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatedLoginTime = getLoginTime().format(format);
		LocalDateTime logoutTime;
		String formatedLogoutTime = null;
		if ((logoutTime = getLogoutTime()) != null)
			formatedLogoutTime = logoutTime.format(format);
		return getUsername() + " " + formatedLoginTime + " " + formatedLogoutTime;
	}
}
