package net.etfbl.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import net.etfbl.dao.PostCategoryDAO;
import net.etfbl.dto.PostCategory;

@ManagedBean(name = "categoryBean")
@javax.faces.bean.RequestScoped
public class PostCategoryBean {
	
	private List<PostCategory> categories;
	
	private String name;
	
	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PostCategoryBean() {
		categories = PostCategoryDAO.getAllActiveCategories();
	}

	public List<PostCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<PostCategory> categories) {
		this.categories = categories;
	}

	public void deleteCategory(Integer id) {
		PostCategoryDAO.delete(id);
		categories.removeIf(category -> category.getId() == id);
	}
	
	public String addCategory() {
		if(!"".equals(this.name)) {
			PostCategory category = new PostCategory(this.name);
			PostCategoryDAO.insert(this.name);
			categories.add(category);
			this.name = "";
		}else {
			this.errorMsg = "Prazno polje";
		}
		return null;
	}
}
