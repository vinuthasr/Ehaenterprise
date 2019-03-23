package com.elephant.model.banner;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.elephant.domain.image.ImageDomain;
import com.elephant.model.image.ImageModel;

public class BannerModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3350064211486543816L;
	private long bannerId;
	private String bannerName;
	private String bannerArea;
	private Date createdDate;
	private Date ModifiedDate;
	private String categoryName;
  
    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private List<ImageModel> image;
    
    
    public List<ImageModel> getImage() {
		return image;
	}

	public void setImage(List<ImageModel> image) {
		this.image = image;
	}

	
    
    
	public long getBannerId() {
		return bannerId;
	}

	public void setBannerId(long bannerId) {
		this.bannerId = bannerId;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getBannerArea() {
		return bannerArea;
	}

	public void setBannerArea(String bannerArea) {
		this.bannerArea = bannerArea;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	
	

}
