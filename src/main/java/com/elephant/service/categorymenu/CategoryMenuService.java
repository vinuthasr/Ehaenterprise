package com.elephant.service.categorymenu;

import java.util.List;

import com.elephant.model.categorymenu.CategoryMenuModel;
import com.elephant.response.Response;

public interface CategoryMenuService {
	public Response addCategoryMenus(CategoryMenuModel categoryMenuModel)throws Exception;
	
	public List<CategoryMenuModel> allCategoryMenus()throws Exception;
	
	public Response updateCategoryMenu(CategoryMenuModel categoryMenuModel);
}
