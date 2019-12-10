package com.elephant.service.categorymenu;

import java.util.List;

import com.elephant.model.categorymenu.CategoryMenuModel;
import com.elephant.response.Response;

public interface CategoryMenuService {
	public Response addCategoryMenus(CategoryMenuModel mainCategoryModel)throws Exception;
	
	public List<CategoryMenuModel> allCategoryMenus()throws Exception;
}
