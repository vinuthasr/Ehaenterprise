package com.elephant.dao.categorymenu;

import java.util.List;

import com.elephant.domain.categorymenu.CategoryMenuDomain;
import com.elephant.response.Response;

public interface CategoryMenuDao {
	CategoryMenuDomain findByCategoryMenuName(String categoryMenuName);
	
	Response addCategoryMenu(CategoryMenuDomain categoryMenuDomain);
	
	public List<CategoryMenuDomain> allCategoryMenu()throws Exception;
	
	Response updateCategoryMenu(CategoryMenuDomain categoryMenuDomain);
}
