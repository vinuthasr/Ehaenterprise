package com.elephant.mapper.categorymenu;

import org.springframework.stereotype.Component;

import com.elephant.domain.categorymenu.CategoryMenuDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.categorymenu.CategoryMenuModel;

@Component
public class CategoryMenuMapper extends AbstractModelMapper<CategoryMenuModel, CategoryMenuDomain>  {

	@Override
	public Class<CategoryMenuModel> entityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<CategoryMenuDomain> modelType() {
		// TODO Auto-generated method stub
		return null;
	}


}
