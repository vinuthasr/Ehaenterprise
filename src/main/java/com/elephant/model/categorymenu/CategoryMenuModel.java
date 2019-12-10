package com.elephant.model.categorymenu;

import java.io.Serializable;
import java.util.Date;

public class CategoryMenuModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int categoryMenuId;
	private String menuName;
	private String description;
	private Date creationDate;
	private Date modifiedDate;
	
	
	public int getCategoryMenuId() {
		return categoryMenuId;
	}
	public void setCategoryMenuId(int categoryMenuId) {
		this.categoryMenuId = categoryMenuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
    
}
