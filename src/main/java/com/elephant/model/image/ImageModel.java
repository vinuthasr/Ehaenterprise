package com.elephant.model.image;

import java.io.Serializable;
import java.util.Date;

import com.elephant.domain.category.Category;
import com.elephant.model.banner.BannerModel;

public class ImageModel implements Serializable{

		

	/**
	 * 
	 */
	private static final long serialVersionUID = -4926909157084139936L;
	
	
	private long imageId;
	private String imageName;
	private String imagePath;
	private String header;
	private String desc1;
	private String desc2;
	private String desc3;
	private String desc4;
	private String desc5;
	private Date createdDate;
	private Date modifiedDate;
	private BannerModel bannerModel;
	private Category categoryModel;
	
	
	public Category getCategoryModel() {
		return categoryModel;
	}
	public void setCategoryModel(Category categoryModel) {
		this.categoryModel = categoryModel;
	}
	public BannerModel getBannerModel() {
		return bannerModel;
	}
	public void setBannerModel(BannerModel bannerModel) {
		this.bannerModel = bannerModel;
	}
	public long getImageId() {
		return imageId;
	}
	public void setImageId(long imageId) {
		this.imageId = imageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getDesc2() {
		return desc2;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	public String getDesc3() {
		return desc3;
	}
	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}
	public String getDesc4() {
		return desc4;
	}
	public void setDesc4(String desc4) {
		this.desc4 = desc4;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
		
	public String getDesc5() {
		return desc5;
	}
	public void setDesc5(String desc5) {
		this.desc5 = desc5;
	}
	
	
}
