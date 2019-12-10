package com.elephant.model.category;

import java.io.Serializable;

import com.elephant.model.categorymenu.CategoryMenuModel;
public class CategoryModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String categoryId;
	private String categoryName;
	private String description;
	private String createdDate;
	private String modifiedDate;
	private String startingDateAndTime;
	private String endingDateAndTime;
	private boolean isActive;
	
	private CategoryMenuModel  categoryMenuDomain;
	
	public String getStartingDateAndTime() {
		return startingDateAndTime;
	}
	public String getEndingDateAndTime() {
		return endingDateAndTime;
	}
	public void setStartingDateAndTime(String startingDateAndTime) {
		this.startingDateAndTime = startingDateAndTime;
	}
	public void setEndingDateAndTime(String endingDateAndTime) {
		this.endingDateAndTime = endingDateAndTime;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public String getDescription() {
		return description;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public CategoryMenuModel getCategoryMenuDomain() {
		return categoryMenuDomain;
	}
	public void setCategoryMenuDomain(CategoryMenuModel categoryMenuDomain) {
		this.categoryMenuDomain = categoryMenuDomain;
	}
	
}