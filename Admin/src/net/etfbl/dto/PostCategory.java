package net.etfbl.dto;

import java.util.List;

public class PostCategory {

	private Integer id;
	private String name;
	public PostCategory(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public PostCategory() {
	}

	public PostCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
