package com.elephant.domain.categorymenu;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.elephant.domain.category.Category;

@Entity
@Table(name = "categoryMenu")
public class CategoryMenuDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="categoryMenuId")
	private int categoryMenuId;
	
	@Column(name="menuName",unique=true)
	private String menuName;
	
	@Column(name="description",length = 5000)
	private String description;
	
	@Column(name="creationDate")
	private Date creationDate;
	
	@Column(name="modifiedDate")	
	private Date modifiedDate;
	
	
	@OneToMany(targetEntity=Category.class, mappedBy="categoryMenuDomain",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Category> category = new HashSet<Category>();
	
	
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

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

	
}
