package com.elephant.domain.image;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.elephant.domain.banner.BannerDomain;
import com.elephant.domain.category.Category;

@Entity
@Table(name="image")
public class ImageDomain  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1911283952538434002L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="imageId")
	private long imageId;
	
	@Column(name="imageName")
	private String imageName;
	
	@Column(name="imagePath")
	private String imagePath;
	
	@Column(name="header")
	private String header;
	
	@Column(name="desc1")
	private String desc1;
	
	@Column(name="desc2")
	private String desc2;
	
	@Column(name="desc3")
	private String desc3;
	
	@Column(name="desc4")
	private String desc4;
	
	@Column(name="desc5")
	private String desc5;
	
	@Temporal(TemporalType.DATE)
	@Column(name="createdDate")
	private Date createdDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="modifiedDate")
	private Date modifiedDate;
	
	@ManyToOne
	@JoinColumn(name="bannerId")
	private BannerDomain bannerDomain;
	
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category categoryDomain;
	
	public Category getCategoryDomain() {
		return categoryDomain;
	}

	public void setCategoryDomain(Category categoryDomain) {
		this.categoryDomain = categoryDomain;
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

	public String getDesc5() {
		return desc5;
	}

	public void setDesc5(String desc5) {
		this.desc5 = desc5;
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

	public BannerDomain getBanner() {
		return bannerDomain;
	}

	public void setBanner(BannerDomain bannerDomain) {
		this.bannerDomain = bannerDomain;
	}


	
	
}
