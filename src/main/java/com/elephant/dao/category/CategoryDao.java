package com.elephant.dao.category;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.elephant.domain.category.Category;
import com.elephant.response.Response;

public interface CategoryDao {

	public Response addCategories(Category update)throws Exception;

	public List<Category> allCategories()throws Exception;

	public Category getCategoryById(String categoryId)throws Exception;

	public Response updateCategory(Category category)throws Exception;

	public Response deleteCategory(String categoryId, boolean isActive)throws Exception;

	public Response deleteCategoryData(String categoryId)throws Exception;

	public List<Category> search(String any)throws Exception;

	public List<Category> allCategoryBtw(Date endingDateAndTime)throws Exception;


	List<Map<String, Object>> getCategoriesByMenus(String menuName); 


}
