package net.etfbl.beans;
 
import java.util.Date;

import javax.faces.bean.ManagedBean;

import net.etfbl.dao.EmergencyHelpDAO;
import net.etfbl.dto.EmergencyHelp;
 

@ManagedBean(name = "postBean")
@javax.faces.bean.RequestScoped
public class PostBean {

	private static final String SUCCESS_MESSAGE = "Uspjesno";

	private static final String ERROR_MESSAGE = "Neuspjesno";

	private String text;

	private Date creationTime;
	
	private String location;

	private String description;
	
	private String imageURL;
	
	private String category;
	
	private String notificationMessage;
	private String color;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}
	
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}

	public String addHelp() {
		EmergencyHelp eh = new EmergencyHelp(this.text, this.creationTime, this.location, this.description, this.imageURL, this.category);
		if(EmergencyHelpDAO.insert(eh)) {
			color = "green";
			this.setNotificationMessage(SUCCESS_MESSAGE);
			clearInputFields();
		}else {
			color = "red";
			this.setNotificationMessage(ERROR_MESSAGE);
		}
	return null;
	}
	
	private void clearInputFields() {
		this.text = "";
		this.creationTime = null;
		this.location = "";
		this.description = "";
		this.imageURL = "";
		this.category = "";
		
}
}
