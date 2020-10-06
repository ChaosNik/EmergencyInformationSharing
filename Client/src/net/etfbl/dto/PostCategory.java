package net.etfbl.dto;

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
