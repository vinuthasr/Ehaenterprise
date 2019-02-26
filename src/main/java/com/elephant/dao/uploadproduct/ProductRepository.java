package com.elephant.dao.uploadproduct;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elephant.domain.category.Category;
import com.elephant.domain.uploadproduct.ProductDomain;

public interface ProductRepository extends JpaRepository<ProductDomain, String>{
	
	public ProductDomain findBySku(String sku);
	

	
	

	
	public List<ProductDomain> findByPriceOrColors(@Param("price") double price, 
															@Param("colors") String colors);
	
	/*@Query(value = "Select * FROM uploadproduct where (types=?1 and colors=?2) and is_active=true", nativeQuery = true)
	List<UploadProductDomain> findByFilter(String types,String colors);*/
	
	/*@Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
	  User findByLastnameOrFirstname(@Param("lastname") String lastname,
	                                 @Param("firstname") String firstname);*/
	// discount=:d or length=:l or price=:p or materialType=:m or fabricPurity=:f or pattern=:ptt or border=:br or borderType=:brt or zariType=:zt or blouse=:bl or blouseColor=:blc or blouseLength=:bll
	
	

}
