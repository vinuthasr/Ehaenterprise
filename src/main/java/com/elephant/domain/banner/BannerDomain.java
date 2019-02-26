package com.elephant.domain.banner;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.image.ImageDomain;

@Entity
@Table(name="banner")
public class BannerDomain  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1722018122920526786L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="bannerId")
	private long bannerId;
	
	@Column(name="bannerName")
	private String bannerName;
	
	@Column(name="bannerArea")
	private String bannerArea;
	
	@Column(name="createdDate")
	private Date createdDate;
	
	@Column(name="ModifiedDate")
	private Date ModifiedDate;
	
	@OneToMany(mappedBy="bannerDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ImageDomain> imageDomain;
	
/*	@ManyToOne
	@JoinColumn(name="customersId")
	private CustomerDomain customer;
	
	
	
	public CustomerDomain getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDomain customer) {
		this.customer = customer;
	}*/

	

	public List<ImageDomain> getImageDomain() {
		return imageDomain;
	}

	public void setImageDomain(List<ImageDomain> imageDomain) {
		this.imageDomain = imageDomain;
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
