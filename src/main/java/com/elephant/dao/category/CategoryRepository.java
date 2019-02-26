package com.elephant.dao.category;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elephant.domain.category.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{
	
	Category findByCategoryId(String categoryId);
	@Query("select c from Category c where categoryName=:categoryName and c.isActive=true")
	//@Query("select category from Category where category.categoryName=:name and u.isActive=true")
	Category findByCategoryName(@Param("categoryName")String categoryName);
	
}

