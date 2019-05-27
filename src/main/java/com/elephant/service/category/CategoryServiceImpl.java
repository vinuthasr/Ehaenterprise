package com.elephant.service.category;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.elephant.constant.StatusCode;
import com.elephant.dao.category.CategoryDao;
import com.elephant.dao.category.CategoryRepository;
import com.elephant.dao.uploadproduct.ProductDao;
import com.elephant.domain.category.Category;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.mapper.category.CategoryMapper;
import com.elephant.mapper.uploadproduct.ProductMapper;
import com.elephant.model.category.CategoryModel;
import com.elephant.model.uploadproduct.ProductModel;
import com.elephant.response.*;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.DateUtility;


@EnableJpaRepositories
@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService{
	
private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	CategoryMapper categoryMapper;
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ProductDao uploadproductdao;

	@Autowired
	CategoryRepository categoryRepository ;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public Response addCategories(CategoryModel model) throws Exception {
		Response response=CommonUtils.getResponseObject("Add Category");
		Category categoryRep = categoryRepository.findByCategoryName(model.getCategoryName());
		if(categoryRep != null) {
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage("Category name already exist");
			return response;
		} else {
			Category update=new Category();
			
			try {
							
	            	BeanUtils.copyProperties(model, update);
		            update.setCategoryId(CommonUtils.generateRandomId());
		            update.setCategoryName(model.getCategoryName());
		            update.setDescription(model.getDescription());
		            update.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
	                update.setCreatedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
	                update.setActive(true);
	              /* try {
	            	   if(new Date().before(new Date(model.getStartingDateAndTime()))) {
	            		   if(new Date(model.getEndingDateAndTime()) != null)
	            			   update.setStartingDateAndTime(new Date(model.getStartingDateAndTime()));}
	                	//else {System.out.println("Enter Correct Date And Time");}
	                	
	                	if(new Date(model.getEndingDateAndTime()).after(new Date(model.getStartingDateAndTime()))) {
	                		if((new Date(model.getStartingDateAndTime()) != null))
	                			update.setEndingDateAndTime(new Date(model.getEndingDateAndTime()));}
	                	//else {System.out.println("Enter Correct Ending Date And Time");}
	                	
	                	/*if(new Date().equals(new Date(model.getEndingDateAndTime()))) {
	                		update.setActive(false);
	                	}
	                }
	               
	                catch (Exception e) {
						e.printStackTrace();	
					} */
	               /* try {
	                	SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	            		Date dateStart=sdf.parse(model.getStartingDateAndTime());
	            		Date dateEnd=sdf.parse(model.getEndingDateAndTime());
	            		Date todayDate=new Date();
	            		
	            		if(todayDate.after(dateStart)) {
						update.setStartingDateAndTime(dateStart);}
	            		else {System.out.println("Enter Correct Date and Time");}
	            		//if(dateStart.after(dateEnd)) 
						update.setEndingDateAndTime(dateEnd);
					} catch (Exception e) {
						e.getMessage();
					}*/
	                response = categoryDao.addCategories(update);
	    			return response;
	    		}
	    		catch (Exception ex) {
	    			logger.info("Exception Service:" + ex.getMessage());
	    		}
		}
		
		return null;
    }
	

	@Override
	public List<CategoryModel> allCategories() throws Exception {
		try {
			List<Category> category=categoryDao.allCategories();
			return categoryMapper.entityList(category);
		}
		catch(Exception e) {logger.info("Exception getProducts:", e);
		
		}
		return null;
	}


	@Override
	public CategoryModel getCategoryById(String categoryId) throws Exception {
		try
		{
			Category category=categoryDao.getCategoryById(categoryId);
			CategoryModel model=new CategoryModel();
			if(category==null)
				return null;
			BeanUtils.copyProperties(category, model);
			return model;
		}
		catch(Exception e)
		{
			logger.info("Exception in getCategoryById",e);
		}
		return null;
	}


	@Override
	public Response updateCategory(CategoryModel model) throws Exception {
		try{
			Category category=new Category();
			BeanUtils.copyProperties(model, category);
			Response res=categoryDao.updateCategory(category);
			return res;
			}
			catch(Exception e){
				logger.info("exception in updateCategory"+e.getMessage());
			}
			return null;
	}


	@Override
	public Response deleteCategory(String categoryId,boolean isActive) throws Exception {
		try
		{
			uploadproductdao.deleteproductByCategoryId(categoryId,isActive);
		return categoryDao.deleteCategory(categoryId,isActive);
		} catch (Exception e) {
			logger.info("Exception deletecategory:"+ e.getMessage());}
			return null;
	}
	
	@Override
	public Response deleteCategoryData(String categoryId) throws Exception {
		Response response = CommonUtils.getResponseObject("Deleted category Data");
		try
		{
			//return categoryDao.deleteCategoryData(categoryId);
			String sql = "DELETE FROM category WHERE category_id = '"+categoryId+"'";
			jdbcTemplate.execute(sql);
			
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(" Category Deleted Successfully");
		} catch (Exception e) {
			logger.info("Exception deletecategory:"+ e.getMessage());
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage("Category should not be able to delete as this is already used in product/image" +e.getMessage());
			return response;
		}
			
			return response;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<ProductModel> getProductsByCategoryName(String categoryName)throws Exception {
		try {
			Category categoryDomain=(Category) categoryRepository.findByCategoryName(categoryName);
			if(categoryDomain==null) 
				 throw new Exception("Entered category name not found !!!");          
			Set<ProductDomain> domainList=categoryDomain.getProduct();
			return productMapper.entityList((List<ProductDomain>) domainList);
		}
		catch(Exception ex){
			logger.info("Exception deletecategory:"+ ex.getMessage());}
		return null;
	}


	@Override
	public List<Category> search(String any, Model model) throws Exception {
		List<Category> searchResults=null;
		try {
			searchResults=categoryDao.search(any);
		} catch (Exception e) {
			logger.info("Exception deletecategory:"+ e.getMessage());
		}
		 model.addAttribute("searchResults", searchResults);
		return searchResults;
	}


	@Override
	public CategoryModel allCategoryBtw(Date endingDateAndTime) throws Exception {
		try
		{
			List<Category> category=categoryDao.allCategoryBtw(endingDateAndTime);
			CategoryModel model=new CategoryModel();
			if(category==null)
				return null;
			BeanUtils.copyProperties(category, model);
			return model;
		}
		catch(Exception e)
		{
			logger.info("Exception in getCategoryById",e);
		}
		return null;
	}
} 

	


	

