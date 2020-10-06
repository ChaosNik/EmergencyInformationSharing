package net.etfbl.dto;

import java.util.Date;
import java.util.List;

public class EmergencyHelp {
	private Integer id;
	private String name;
	private Date time; 
	private String location;
	private String description;
	private String image;
	private String category;
	
	public EmergencyHelp(Integer id, String name, Date time, String location, String description, String image, String category) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.location = location;
		this.description = description;
		this.image = image;
		this.category = category;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	} 
}

