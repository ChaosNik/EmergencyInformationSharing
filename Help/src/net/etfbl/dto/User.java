package net.etfbl.dto;

import java.io.Serializable;
import java.util.Arrays;

public class User implements Serializable {


	private static final long serialVersionUID = 1L;
	private Integer id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String mail;
	private String country;
	private String region;
	private String city;
	private String photo;
    private Integer numberOfLogins;
    private Integer notificationOnMail;
    private Integer notificationInApp;

    public User(Integer id, String firstName, String lastName, String username, String password, String mail,
    		String photo, String country, String region, String city, Integer notificationOnMail, Integer notificationInApp, Integer numberOfLogins) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.country = country;
		this.region = region;
		this.city = city;
		this.photo = photo;
		this.notificationOnMail = notificationOnMail;
		this.notificationInApp = notificationInApp;
		this.numberOfLogins = numberOfLogins;
	}
    
    @Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", mail=" + mail + ", country=" + country + ", region=" + region
				+ ", city=" + city + ", photo=" + photo + ", numberOfLogins=" + numberOfLogins + ", notificationOnMail="
				+ notificationOnMail + ", notificationInApp=" + notificationInApp + "]";
	}

	public Integer getNotificationOnMail() {
		return notificationOnMail;
	}

	public void setNotificationOnMail(Integer notificationOnMail) {
		this.notificationOnMail = notificationOnMail;
	}

	public Integer getNotificationInApp() {
		return notificationInApp;
	}

	public void setNotificationInApp(Integer notificationInApp) {
		this.notificationInApp = notificationInApp;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public User(String firstName, String lastName, String username, String password, String mail) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.mail = mail;
	}
	
	 
		public User(Integer id, Integer numberOfLogins, String username, String firstName, String lastName, String photo) {
			super();
			this.id = id;
			this.numberOfLogins = numberOfLogins;
			this.username = username;
			this.firstName = firstName;
			this.lastName = lastName;
			this.photo = photo;
		}

	public User() {}

	public User(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoto() {
		return photo;
	}

	public void setphoto(String photo) {
		this.photo = photo;
	}

	public Integer getNumberOfLogins() {
		return numberOfLogins;
	}

	public void setNumberOfLogins(Integer numberOfLogins) {
		this.numberOfLogins = numberOfLogins;
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result +((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (!photo.equals(other.photo))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
