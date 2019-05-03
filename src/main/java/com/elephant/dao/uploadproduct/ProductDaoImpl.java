package com.elephant.dao.uploadproduct;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elephant.constant.StatusCode;
import com.elephant.dao.category.CategoryDao;
import com.elephant.dao.category.CategoryRepository;
import com.elephant.domain.category.Category;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.model.category.CategoryModel;
import com.elephant.model.uploadproduct.ProductModel1;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.DateUtility;





@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {
	private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);
	@PersistenceContext
	EntityManager entitymanager;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Override
//	public List<ProductDomain> getProductByCatagory(String categoryName, String colors, Float discount, Double length,
//			double min,double max, String materialType, String fabricPurity, String pattern, String border, String borderType,
//			String zariType, String blouse, String blouseColor, Double blouseLength) throws Exception {		
//			//Response res =new Response();
			//try{
	
	
	public List<ProductDomain> getProductByCatagory1(ProductModel1 pm1) {
			System.out.println();
			String parentHql =  "select u FROM ProductDomain u inner join u.category c where (";
			Boolean isOrRequired = false;
			Boolean isTypes = false;
			Boolean isColors = false;	
			Boolean isDiscount = false;
			Boolean isLength = false;
			Boolean isPrice = false;
			Boolean isMaterialType = false;
			Boolean isFabricPurity = false;
			Boolean isPattern = false;
			Boolean isBorder = false;
			Boolean isBorderType = false;
			Boolean isZariType = false;
			Boolean isBlouse = false;
			Boolean isBlouseColor = false;
			Boolean isBlouseLength = false;
			
           String c=new String();
			for(String c1:pm1.getCategoryName()) {
                
				  c=c.concat("'"+c1+"'"+",");
				  
				}
			
			 String categoryname=new String();
			for(int i=0;i<c.length()-1;i++) {
				categoryname+=c.charAt(i);
			  
			}
			
			
			
			
			
		
			
			
			
		
			if(!pm1.getCategoryName().isEmpty()) {
				if(! categoryname.equalsIgnoreCase("string")) {
					categoryname=categoryname.replace("string", "''");
				
				
			
				parentHql +=  "c.categoryName in ("+categoryname+")";
				parentHql+="and";
				//parentHql +=  "c.categoryName in (:t)";
				isTypes = true;
				isOrRequired = true;
			}}
			
			if(!pm1.getColors().isEmpty()) {
			
					
					   String c1=new String();
						for(String c2:pm1.getColors()) {
			                
							  c1=c1.concat("'"+c2+"'"+",");
							  
							}
						 String colors=new String();
						for(int i=0;i<c1.length()-1;i++) {
							colors+=c1.charAt(i);
						  
						}
				
					//parentHql += " and ";
						if(! colors.equalsIgnoreCase("'string'")) {
							String colors1=colors.replace("string", " ");
				isColors = true;
				//parentHql +=  " u.colors=:c ";
				parentHql +=  " u.colors in ("+colors1+") ";
				parentHql+="and";

				}}
			
			if(null != pm1.getDiscount()) {
				
				if(pm1.getDiscount() != 0.0) {
					Float discount1=pm1.getDiscount();
				   
					//parentHql += " and ";
				isDiscount = true;
				//parentHql +=  " u.discount=:hd ";
				parentHql +=  " u.discount between 0 and "+discount1+" ";
				parentHql+="and";

				
			}}
			
			
			if(null != pm1.getLength()) {
				if(pm1.getLength() != 0.0) {
				//if(isOrRequired)
					Double length1=pm1.getLength();
					//parentHql += " and ";
				isLength = true;
				//parentHql +=  " u.length=:l ";
				parentHql +=  " u.length in ("+length1+") ";
				parentHql+="and";

			}}
			
			//if( (max != null) & (min != null) ) {
				//if(isOrRequired)
				if((pm1.getMin() != 0.0) & (pm1.getMax() != 0.0)) {
				  double min1=pm1.getMin();
				  
				  
				  double max1=pm1.getMax();
					//parentHql += " and ";
				isPrice = true;
				//parentHql +=  " u.price=:p ";
//				parentHql +=  " u.price BETWEEN ("+min1 +" AND " +max1+") ";

				parentHql +=  " u.price between "+min1+" and "+max1+" ";
				parentHql+="and";

				

			
				}
			if(null != pm1.getMaterialType()) {
				if(! pm1.getMaterialType().equals("string")) {
					String materialType1= pm1.getMaterialType();
				//if(isOrRequired)
					//parentHql += " and ";
				isMaterialType = true;
				//parentHql +=  " u.materialType=:m ";
				parentHql +=  " u.materialType in ("+materialType1 +") ";
				parentHql+="and";

			
			}}
			
			if(null != pm1.getFabricPurity()) {
				if(! pm1.getFabricPurity().equals("string")) {
					
				//if(isOrRequired)
					String fabricPurity1=pm1.getFabricPurity();
					//parentHql += " and ";
				isFabricPurity = true;
				//parentHql +=  " u.fabricPurity=:f ";
				parentHql +=  " u.fabricPurity in ("+fabricPurity1+") ";
				parentHql+="and";

				
			}}
			
			if(null != pm1.getPattern()) {
				if(! pm1.getPattern().equals("string")) {
					String  pattern1=pm1.getPattern();
				//if(isOrRequired)
					//parentHql += " and ";
				isPattern = true;
				//parentHql +=  " u.pattern=:pt ";
				parentHql +=  " u.pattern in ("+pattern1+") ";
				parentHql+="and";

			}}
			
			if(null != pm1.getBorder()) {
				if(! pm1.getBorder().equals("string")) {
				//if(isOrRequired)
					String  border1= pm1.getBorder();
					//parentHql += " and ";
				isBorder = true;
				//parentHql +=  " u.border=:b ";
				parentHql +=  " u.border in ("+border1+") ";
				parentHql+="and";

			}}
			
			if(null != pm1.getBorderType()) {
				if(! pm1.getBorderType().equals("string")) {
				//if(isOrRequired)
					String borderType1=pm1.getBorderType();
					//parentHql += " and ";
				isBorderType = true;
				//parentHql +=  " u.borderType=:bt ";
				parentHql +=  " u.borderType in ("+borderType1+") ";
				parentHql+="and";

			}}
			
							
			if(null != pm1.getZariType()) {
				//if(isOrRequired)
				if(! pm1.getZariType().equals("string")) {
					String zariType1= pm1.getZariType();
					//parentHql += " and ";
				isZariType = true;
				//parentHql +=  " u.zariType=:z ";
				parentHql +=  " u.zariType in ("+zariType1+") ";
				parentHql+="and";

			}}
			
			if(null != pm1.getBlouse()) {
				//if(isOrRequired)
				if(! pm1.getBlouse().equals("string")) {
					String blouse1=pm1.getBlouse();
					//parentHql += " and ";
				isBlouse = true;
				//parentHql +=  " u.blouse=:bl ";
				parentHql +=  " u.blouse in ("+blouse1+") ";
				parentHql+="and";

			}}
			
			if(null != pm1.getBlouseColor()) {
				//if(isOrRequired)
				if(! pm1.getBlouseColor().equals("string")) {
					String blouseColor1=pm1.getBlouseColor();
					//parentHql += " and ";
				isBlouseColor = true;
				//parentHql +=  " u.blouseColor=:bc ";
				parentHql +=  " u.blouseColor in ("+blouseColor1+") ";
				parentHql+="and";

			}}
			
			if(null != pm1.getBlouseLength()) {
				if(pm1.getBlouseLength() != 0.0) {
					double blouseLength1=pm1.getBlouseLength();
				//if(isOrRequired)
					//parentHql += " and ";
				isBlouseLength = true;
				//parentHql +=  " u.blouseLength=:bh ";
				parentHql +=  " u.blouseLength in ("+blouseLength1+") ";
				parentHql+=" and";

			}}
			
			
			parentHql +=  "  c.isActive=true and u.isActive=true)";
			
			javax.persistence.Query qry = entitymanager.createQuery(parentHql);
			
			System.out.println(parentHql);

//			if(isTypes)
//				qry.setParameter("t",categoryName);
//			    
			    

//			if(isColors)
//				qry.setParameter("co", colors);
//			
//			if(isDiscount)
//				qry.setParameter("hd", discount);
//			
//			if(isLength)
//				qry.setParameter("l", length);
//			
//			if(isPrice)
//				qry.setParameter("p", price);
//			
//			if(isMaterialType)
//				qry.setParameter("m", materialType);
//			
//			if(isFabricPurity)
//				qry.setParameter("f", fabricPurity);
//
//			if(isPattern)
//				qry.setParameter("pt", pattern);
//			
//			if(isBorder)
//				qry.setParameter("d", border);
//			
//			if(isBorderType)
//				qry.setParameter("l", borderType);
//			
//			if(isZariType)
//				qry.setParameter("z", zariType);
//
//			if(isBlouse)
//				qry.setParameter("bl", blouse);
//
//			if(isBlouseColor)
//				qry.setParameter("bc", blouseColor);
//			
//			if(isBlouseLength)
//				qry.setParameter("bh", blouseLength);
//			
//			
			List<ProductDomain> domains =  (List<ProductDomain>) qry.getResultList();
			return domains;
}
			/*try {
				String hql = "select u from UploadProductDomain u inner join Category c on u.category_id = c.category_id where c.categoryName=:cname and u.blouseColor=:a and c.isActive=true";
				return (List<UploadProductDomain>) entitymanager.createQuery(hql).setParameter("cname", categoryName).getResultList();
			} catch (EmptyResultDataAccessExcption e) {
				return null;
			} catch (Exception e) {
				logger.error("Exception in getProductByBorderType", e);
				return null;
			}
		}*/

				
				
				//List<UploadProductDomain> x = uploadProductrepository.findByFilter(types,colors);
				
				//String hql = "types=:t or colors=:c or discount=:d or length=:l or price=:p or materialType=:m or fabricPurity=:f or pattern=:ptt or border=:br or borderType=:brt or zariType=:zt or blouse=:bl or blouseColor=:blc or blouseLength=:bll )and isActive=true ";

				
				//EntityTransaction t = entitymanager.getTransaction();
		        //t.begin();
				//String hql = "FROM UploadProductDomain where (types=:t, colors=:c, discount=:d)";//or length=:l or price=:p or materialType=:m or fabricPurity=:f or pattern=:ptt or border=:br or borderType=:brt or zariType=:zt or blouse=:bl or blouseColor=:blc or blouseLength=:bll) and isActive=true ";
		        
		        //t.commit();
		        /*}catch(Exception e){
				response.setStatus(StatusCode.SUCCESS.name());
				logger.info("Exception in getproductBytype");
				response.setStatus(e.getMessage());
			}*/
				
		
	
	
	/*----------------------------------Add Product-------------------------------------*/
	
	@Override
	public Response addproduct(ProductDomain update) throws Exception {
		Response response = CommonUtils.getResponseObject("Add products data");
		try {
			entitymanager.persist(update);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(" products added Successfully");
		} catch (Exception e) {
			logger.error("Exception in adding products", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
			response.setMessage("Failed to add products ");
		}
		return  response;
	}
	
	/*----------------------------------Update Product -------------------------------------*/
	
	@Override
	public Response updateProduct(ProductDomain update) throws Exception {
		
		Response response = CommonUtils.getResponseObject("Update product data");
		try {
			//List<UploadProductDomain> upload=getProductById(update.getProductId());
			ProductDomain up=getProductById(update.getProductId());
			up.setColors(update.getColors());
			up.setActive(true);
			up.setDiscount(update.getDiscount());
			up.setOccassion(update.getOccassion());
			up.setPrice(update.getPrice());
			up.setInStock(update.getInStock());
			up.setQuantity(update.getQuantity());
			up.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
			up.setSku(update.getSku());
			up.setCollectionDesc(update.getCollectionDesc());
			up.setMaterialType(update.getMaterialType());
			
			
			up.setFabricPurity(update.getFabricPurity());
			up.setPattern(update.getPattern());
			up.setBorder(update.getBorder());
			up.setBorderType(update.getBorderType());
			up.setZariType(update.getZariType());
			up.setLength(update.getLength());
			up.setBlouseColor(update.getBlouseColor());
			up.setBlouseLength(update.getBlouseLength());
			up.setHeaderDesc(update.getHeaderDesc());
			
			up.setMainImageUrl(update.getMainImageUrl());
			
			entitymanager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		} catch (Exception e) {
			logger.error("Exception in updateCourse", e);
			DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS);
			response.setStatus(StatusCode.ERROR.name());

			response.setErrors(e.getMessage());
		}
		return response;
	}
/*----------------------------------Delete product-------------------------------------*/

	@Override
	public Response deleteproduct(String productId) throws Exception {

		Response response = CommonUtils.getResponseObject("Deleted"+productId);
		try {
			ProductDomain up = entitymanager.find(ProductDomain.class,productId);
			up.setActive(false);
			
			//entitymanager.remove(up);
			entitymanager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage(" deleteproduct deleted Successfully");
		} catch (Exception e) {
			logger.error("Exception in deleteproduct", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
			response.setMessage("Failed to deleteproduct   ");
		}
		return response;
		
		
	}
	
@Override
public Response deleteProduct(String productId, boolean isActive) throws Exception {
	Response response = CommonUtils.getResponseObject("Product Deleted");
	try {
		ProductDomain domain = getProductById(productId);
		domain.setActive(isActive);
		
		entitymanager.flush();
		response.setStatus(StatusCode.SUCCESS.name());
	} catch (Exception e) {
		logger.info("Exception in deleteProduct", e);
		response.setStatus(StatusCode.ERROR.name());
		response.setErrors(e.getMessage());
	}
	return response;
	}
			
	/*----------------------------------Get Product By Id-------------------------------------*/

@Override
public ProductDomain getProductById(String productId) throws Exception {
	try {
		String hql = "FROM ProductDomain where productId=?1 and isActive=true";
		return (ProductDomain) entitymanager.createQuery(hql).setParameter(1, productId).getSingleResult();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproduct", e);
		return null;
	}
}


/*----------------------------------Get Product By Discounts-------------------------------------*/

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByDiscount(float discount) throws Exception {
	try {
		String hql = "FROM ProductDomain where discount=:dis and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("dis", discount).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductType", e);
		return null;
	}
}

/*----------------------------------Excel Upload File-------------------------------------*/


@Override
public List<ProductDomain> uploadFile(List<ProductDomain> productuploadList) throws Exception {
	entitymanager.persist(productuploadList);
	return productuploadList;
}

@Override
public Response save(ProductDomain update) throws Exception {
	
	Response response = CommonUtils.getResponseObject("Add products data");
	try {
		entitymanager.persist(update);
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage(" products added Successfully");
	} catch (Exception e) {
		logger.error("Exception in adding products", e);
		response.setStatus(StatusCode.ERROR.name());
		response.setErrors(e.getMessage());
		response.setMessage("Failed to add products ");
	}
	return response;
}



@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByColor(String colors) throws Exception {
	try {
		String hql = "FROM ProductDomain where colors=:col and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("col", colors).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductType", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByOccassion(String occassion) throws Exception {
	try {
		String hql = "FROM ProductDomain where occassion=:occ and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("occ", occassion).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByOccassion", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByPrice(double price) throws Exception {
	try {
		String hql = "FROM ProductDomain WHERE (price BETWEEN 999 AND 3001) or price between 2999 and 5001 or price between 3999 and 6001";
		return (List<ProductDomain>) entitymanager.createQuery(hql).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductprice", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByPriceRange(double minPrice,double maxPrice) throws Exception {
	try {
		
		String hql = "FROM ProductDomain where price BETWEEN :min AND :max and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("min", minPrice).setParameter("max", maxPrice).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductpriceRange", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductsByPriceRange(String categoryName,double minPrice,double maxPrice) throws Exception {
	try {
		
		String hql = "select domain FROM ProductDomain domain inner join domain.category c where c.categoryName=:name and price BETWEEN :min AND :max and domain.isActive=true ";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("name", categoryName).setParameter("min", minPrice).setParameter("max", maxPrice).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductpriceRange", e);
		return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByMaterialType(String materialType) throws Exception {
	try {
		String hql = "FROM ProductDomain where materialType=:mat and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("mat", materialType).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByMaterialType", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByBorder(String border) throws Exception {
	try {
		String hql = "FROM ProductDomain where border=:bor and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("bor", border).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByBorder", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByBorderType(String borderType) throws Exception {
	try {
		String hql = "FROM ProductDomain where borderType=:bortype and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("bortype", borderType).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByBorderType", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByZariType(String zariType) throws Exception {
	try {
		String hql = "FROM ProductDomain where zariType=:zartype and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("zartype", zariType).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByZariType", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByBlouse(String blouse) throws Exception {
	try {
		String hql = "FROM ProductDomain where blouse=:blou and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("blou", blouse).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByBlouse", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByPattern(String pattern) throws Exception {
	try {
		String hql = "FROM ProductDomain where pattern=:patt and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("patt", pattern).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByPattern", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByFabricPurity(String fabricPurity) throws Exception {
	try {
		String hql = "FROM ProductDomain where fabricPurity=:fabricPur and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("fabricPur", fabricPurity).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByFabricPurity", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByBlouseColor(String blouseColor) throws Exception {
	try {
		String hql = "FROM ProductDomain where blouseColor=:bc and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("bc", blouseColor).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByBlouseColor", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByBlouseLength(Double blouseLength) throws Exception {
	try {
		String hql = "FROM ProductDomain where blouseLength=:bl and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("bl", blouseLength).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByBlouseLength", e);
		return null;
	}
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductBySareeLength(Double length) throws Exception {
	try {
		String hql = "FROM ProductDomain where length=:l and isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("l", length).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductBySareeLength", e);
		return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProducts() throws Exception {
	try {
		String hql ="from ProductDomain where isActive=true";
		return (List<ProductDomain>) entitymanager.createQuery(hql).getResultList();
		 
	}
	catch(HibernateException e) {logger.info("Exception in get Products" +e.getMessage());
	return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByFilterDiscount(float discount) throws Exception {
	try {
		String hql = "FROM ProductDomain where discount >=:dis and isActive=true order by discount";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("dis", discount).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getProductByFilterDiscount", e);
		return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getPriceSortingLowToHigh() throws Exception {
	try {
		String hql ="FROM ProductDomain where isActive=true ORDER BY price";
		return (List<ProductDomain>) entitymanager.createQuery(hql).getResultList();
	}
	catch(Exception e) {logger.info("Exception in get Products" +e.getMessage());
	return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getPriceSortingHighToLow() throws Exception {
	try {
		String hql ="FROM ProductDomain where isActive=true ORDER BY price DESC ";
		return (List<ProductDomain>) entitymanager.createQuery(hql).getResultList();
	}
	catch(Exception e) {logger.info("Exception in get Products" +e.getMessage());
	return null;
	}
	
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByTypesSortingPriceLowToHigh(String types) throws Exception {
	try {
		String hql = "Select domain FROM ProductDomain domain inner join domain.category c WHERE c.categoryName=:typ and c.isActive=true and domain.isActive=true ORDER BY price";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("typ", types).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductCategoryNameSortingPriceLowToHigh", e);
		return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductByTypesSortingPriceHighToLow(String types) throws Exception {
	try {
		String hql = "Select domain FROM ProductDomain domain inner join domain.category c WHERE c.categoryName=:name and c.isActive=true and domain.isActive=true ORDER BY price DESC ";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("name", types).getResultList();
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (Exception e) {
		logger.error("Exception in getproductCategoryNameSortingPriceHighToLow", e);
		return null;
	}
}


@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getByNewProducts() throws Exception {
	try {
		String hql =" FROM ProductDomain WHERE modifiedDate IN (SELECT max(modifiedDate) FROM UploadProductDomain group by c.categoryName) order by modifiedDate desc ";
		return (List<ProductDomain>) entitymanager.createQuery(hql).getResultList();
		/*Query q = entitymanager.createQuery("FROM UploadProductDomain order by modified_date");
		q.setFirstResult(0);
		q.setMaxResults(10);
		List<UploadProductDomain> l = q.getResultList();
		return l;*/
	}
	catch(HibernateException e) {logger.info("Exception in get Products" +e.getMessage());
	return null;
	}	
}

@SuppressWarnings("unchecked")
@Override
public List<ProductDomain> getProductBycategoryId(String categoryId) throws Exception {
	try {
		//String hql ="select upload from UploadProductDomain upload, Category category where category.categoryId =:cat and category.isActive=true";
		//String hql ="select upload from ProductDomain upload inner join upload.category c where c.categoryId =:cat and category.isActive=true";
		
		String hql ="select u FROM ProductDomain u inner join u.category c where c.categoryId=:cat and c.isActive=true ";
		return (List<ProductDomain>) entitymanager.createQuery(hql).setParameter("cat", categoryId).getResultList();
		  // return List<ProductDomain> pd=  productRepository.findByCategoryId(categoryId);
	} catch (EmptyResultDataAccessException e) {
		return null;
	} catch (HibernateException e) {
		logger.error("Exception in getProductBycategoryId", e);
		return null;
	}
	
}


@Override
public Response deleteproductByCategoryId(String categoryId, boolean isActive) throws Exception {
		
	Response response = CommonUtils.getResponseObject("Delete Product data");
	try {
		Set<ProductDomain> domain = categoryRepository.findByCategoryId(categoryId).setActive(isActive);
		for(ProductDomain dom: domain )
		{
			dom.setActive(isActive);
			entitymanager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		}
	} catch (Exception e) {
		logger.info("Exception in deleteProduct", e);
		response.setStatus(StatusCode.ERROR.name());
		response.setErrors(e.getMessage());
	}
	return response;
	
}





}
