package net.etfbl.beans;

import java.util.List;

import net.etfbl.dao.PostCategoryDAO;
import net.etfbl.dto.PostCategory;

public class PostCategoryBean {

	public List<PostCategory> getAllActivePostCategories() {
		return PostCategoryDAO.getAllActiveCategories();
	}

}
