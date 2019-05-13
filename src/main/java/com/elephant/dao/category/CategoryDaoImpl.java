package com.elephant.dao.category;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elephant.constant.StatusCode;
import com.elephant.domain.category.Category;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.model.category.CategoryModel;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.DateUtility;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;


@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {
	private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Response addCategories(Category update) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Category Data");
		try {
			entityManager.persist(update);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(" Category Added Successfully");
		} catch (Exception e) {
			logger.error("Exception in adding category", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
			response.setMessage("Failed to add category ");
		}
		return  response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> allCategories() throws Exception {
		try {
			String hql ="from Category where isActive=true";
			return (List<Category>) entityManager.createQuery(hql).getResultList();
		}
		catch(HibernateException e) {logger.info("Exception in Category" +e.getMessage());
		return null;
		}
	}

	@Override
	public Category getCategoryById(String categoryId) throws Exception {
		Response response=CommonUtils.getResponseObject("Get Category By Id");
		try
		{
			return entityManager.find(Category.class, categoryId);
		}
		catch(Exception e)
		{
			logger.error("Exception in getCategoryById",e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return null;
	}

	@Override
	public Response updateCategory(Category category) throws Exception {
		Response response = CommonUtils.getResponseObject("Update Category Data");
		try {
			Category cat = getCategoryById(category.getCategoryId());
			cat.setCategoryName(category.getCategoryName());
			cat.setDescription(category.getDescription());
			cat.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
			
						
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		} catch (Exception e) {
			logger.info("exception in updateCategory" + e);
			response.setStatus(StatusCode.ERROR.name());
			response.setStatus(e.getMessage());
		}
		return response;

	}

	@Override
	public Response deleteCategory(String categoryId,boolean isActive) throws Exception {
		/*Response response = CommonUtils.getResponseObject("Delete Category Data");

		try {

			String hql = " DELETE FROM Category WHERE categoryId=:del";
			entityManager.createQuery(hql).setParameter("del", categoryId).executeUpdate();
			return new Response();

		} catch (Exception e) {
			logger.error("Exception in deleteCategory", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}

		return response;
		
	}*/
		Response response = CommonUtils.getResponseObject("Deleted Product Data");
		try {
			//Category up = entityManager.find(Category.class,categoryId);
			Category up=getCategoryById(categoryId);
			up.setActive(isActive);
			
			//entityManager.remove(up);
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(" Category Deleted Successfully");
		} catch (Exception e) {
			logger.error("Exception in deleteCategory", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
			response.setMessage("Failed to delete Category   ");
		}
		return response;
	}

	
	@Override
	public Response deleteCategoryData(String categoryId) throws Exception {
		Response response = CommonUtils.getResponseObject("Deleted"+categoryId);
		try {
//			Category up = entityManager.find(Category.class,categoryId);
//			//up.setActive(true);
//			
//			entityManager.remove(up);
//			//entityManager.flush();
			categoryRepository.deleteById(categoryId);
			
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(" Category Deleted Successfully");
		} catch (Exception e) {
			logger.error("Exception in deleteCategory", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
			response.setMessage("Failed to delete Category   ");
		}
		return response;
	}

	@Override
	public List<Category> search(String text) throws Exception {
		Response response = CommonUtils.getResponseObject("Searching elements");
		//try {
		return null;

//			FullTextEntityManager fullTextEntityManager = 
//					org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
//
//			/*QueryBuilder qb = fullTextEntityManager.getSearchFactory()
//			        .buildQueryBuilder().forEntity(Category.class).get();
//
//			org.apache.lucene.search.Query query = qb
//			      .keyword()
//			        .wildcard()
//			      .onFields("categoryName", "product.colors")
//			      .matching("*"+text.toLowerCase()+"*")
//			      .createQuery();*/
//			QueryBuilder categoryQb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity( Category.class ).get();
//			QueryBuilder productsQb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity( ProductDomain.class ).get();
//			
//			Query categoryQuery = categoryQb.keyword()
//			    .onFields( "categoryName")
//			    .matching( text )
//			    .createQuery();
//			Query productsQuery = productsQb.keyword()
//			    .onFields( "colors","occassion" )
//			    .matching( text )
//			    .createQuery();
//			
//			Query query = categoryQb.bool()
//			        .should( categoryQuery )
//			        .should( productsQuery )
//			        .createQuery();
//			
//
//			javax.persistence.Query jpaQuery =
//			        fullTextEntityManager.createFullTextQuery(query, Category.class,ProductDomain.class);
//
//			@SuppressWarnings("unchecked")
//			List<Category> results = jpaQuery.getResultList();
//	    return results;
//		}
//		catch(Exception e)
//		{
//			logger.error("Exception in ProductSearch",e);
//			response.setStatus(StatusCode.ERROR.name());
//			response.setErrors(e.getMessage());
//		}
//		return null;
  }


	@SuppressWarnings("unchecked")
	@Override
	public List<Category> allCategoryBtw(Date endingDateAndTime) throws Exception {
		try {
			String hql ="from Category WHERE endingDateAndTime=endingDateAndTime  AND isActive=true";
			//String hql ="FROM Category AS c WHERE c.categoryName BETWEEN startingDateAndTime AND endingDateAndTime and isActive=true";
			return (List<Category>) entityManager.createQuery(hql).getResultList();
		}
		catch(HibernateException e) {logger.info("Exception in Category" +e.getMessage());
		return null;
		}
	}

		
}
