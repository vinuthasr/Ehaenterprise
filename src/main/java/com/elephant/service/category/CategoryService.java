package com.elephant.service.category;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.ui.Model;

import com.elephant.domain.category.Category;
import com.elephant.model.category.CategoryModel;
import com.elephant.model.uploadproduct.ProductModel;
import com.elephant.response.Response;

public interface CategoryService {

	public Response addCategories(CategoryModel model)throws Exception;

	public List<CategoryModel> allCategories()throws Exception;

	public CategoryModel getCategoryById(String categoryId)throws Exception;

	public Response updateCategory(CategoryModel model)throws Exception;

	public Response deleteCategory(String categoryId, boolean isActive)throws Exception;

	public List<ProductModel> getProductsByCategoryName(String categoryName) throws Exception;

	public Response deleteCategoryData(String categoryId) throws Exception;

	List<Category> search(String any, Model model) throws Exception;

	//public List<CategoryModel> allCategoryBtw()throws Exception;

CategoryModel allCategoryBtw(Date endingDateAndTime) throws Exception;

	
}
