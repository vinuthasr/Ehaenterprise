package com.elephant.service.categorymenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.elephant.constant.StatusCode;
import com.elephant.dao.categorymenu.CategoryMenuDao;
import com.elephant.domain.categorymenu.CategoryMenuDomain;
import com.elephant.mapper.categorymenu.CategoryMenuMapper;
import com.elephant.model.categorymenu.CategoryMenuModel;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;


@EnableJpaRepositories
@Service("CategoryMenuService")
public class CategoryMenuServiceImpl implements CategoryMenuService{

	private static final Logger logger = LoggerFactory.getLogger(CategoryMenuServiceImpl.class);
	
	@Autowired
	CategoryMenuDao categoryMenuDao;
	
	@Autowired
	CategoryMenuMapper categoryMenuMapper;
	
	@Override
	public Response addCategoryMenus(CategoryMenuModel mainCategoryModel) throws Exception {
		Response response=CommonUtils.getResponseObject("Add Category Menus");
		
		CategoryMenuDomain categoryMenuDomain = categoryMenuDao.findByCategoryMenuName(mainCategoryModel.getMenuName());
		if(categoryMenuDomain != null) {
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage("Category Menu Name already exist");
			return response;
		} else {
			categoryMenuDomain = new CategoryMenuDomain();
			try {
				BeanUtils.copyProperties(mainCategoryModel, categoryMenuDomain);
				categoryMenuDomain.setCreationDate(new Date());
				categoryMenuDomain.setModifiedDate(new Date());
				
				response = categoryMenuDao.addCategoryMenu(categoryMenuDomain);
			}catch (Exception ex) {
				logger.info("Exception in CategoryMenuServiceImpl Service:" + ex.getMessage());
			}
		}
		
		return response;
	}


	@Override
	public List<CategoryMenuModel> allCategoryMenus() throws Exception {
		try {
			List<CategoryMenuDomain> categoryMenuDomainList = categoryMenuDao.allCategoryMenu();
			List<CategoryMenuModel> categoryMenuModelList = new ArrayList<CategoryMenuModel>();
			CategoryMenuModel categoryMenuModel = null; 
			for(CategoryMenuDomain categoryMenuDomain:categoryMenuDomainList) {
				categoryMenuModel = new CategoryMenuModel();
				categoryMenuModel.setCategoryMenuId(categoryMenuDomain.getCategoryMenuId());
				categoryMenuModel.setCreationDate(categoryMenuDomain.getCreationDate());
				categoryMenuModel.setDescription(categoryMenuDomain.getDescription());
				categoryMenuModel.setMenuName(categoryMenuDomain.getMenuName());
				categoryMenuDomain.setModifiedDate(categoryMenuDomain.getModifiedDate());
				
				categoryMenuModelList.add(categoryMenuModel);
			}
					//categoryMenuMapper.entityList(categoryMenuDomainList);
			return categoryMenuModelList;
		}
		catch(Exception e) {
			logger.info("Exception in CategoryMenuServiceImpl service:", e);
		}
		return null;
	}


	@Override
	public Response updateCategoryMenu(CategoryMenuModel categoryMenuModel) {
		try {
			CategoryMenuDomain categoryMenuDomain = new CategoryMenuDomain();
			BeanUtils.copyProperties(categoryMenuModel, categoryMenuDomain);
			Response response = categoryMenuDao.updateCategoryMenu(categoryMenuDomain);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	

}
