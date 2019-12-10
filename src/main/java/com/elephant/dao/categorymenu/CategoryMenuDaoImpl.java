package com.elephant.dao.categorymenu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elephant.constant.StatusCode;
import com.elephant.domain.categorymenu.CategoryMenuDomain;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;

@Repository
@Transactional
public class CategoryMenuDaoImpl implements CategoryMenuDao{
	private static final Logger logger = LoggerFactory.getLogger(CategoryMenuDaoImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CategoryMenuDomain findByCategoryMenuName(String categoryMenuName) {
		try {
			String sql = "SELECT * FROM categoryMenu where menuName=?";
			return (CategoryMenuDomain) jdbcTemplate.queryForObject(sql, new Object[] { categoryMenuName },
					new BeanPropertyRowMapper(CategoryMenuDomain.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in findByCategoryMenuName", e);
			return null;
		}
	}

	@Override
	public Response addCategoryMenu(CategoryMenuDomain categoryMenuDomain) {
		Response response = CommonUtils.getResponseObject("Add Category Menu data");
		try {
			String sql = "INSERT INTO category_menu (menu_Name,description,creation_Date,modified_Date) VALUES(?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] {  categoryMenuDomain.getMenuName(), categoryMenuDomain.getDescription(), categoryMenuDomain.getCreationDate(),
							categoryMenuDomain.getModifiedDate() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addCategoryMenu", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CategoryMenuDomain> allCategoryMenu() throws Exception {
		try {
			String sql = "SELECT * FROM category_menu";
			List<CategoryMenuDomain> categoryMenuDomainList = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<CategoryMenuDomain>(CategoryMenuDomain.class));
			return categoryMenuDomainList;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in allCategoryMenu", e);
			return null;
		}
	}

	
}
