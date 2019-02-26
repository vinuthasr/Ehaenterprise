package com.elephant.mapper.category;

import org.springframework.stereotype.Component;

import com.elephant.domain.category.Category;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.category.CategoryModel;

	@Component
	public class CategoryMapper extends AbstractModelMapper<CategoryModel, Category> {

		@Override
		public Class<Category> modelType() {
			return Category.class;
		}

		

		@Override
		public Class<CategoryModel> entityType() {
			return CategoryModel.class;
		}
	}
	
	