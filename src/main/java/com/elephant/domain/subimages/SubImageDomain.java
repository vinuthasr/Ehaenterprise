package com.elephant.domain.subimages;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.elephant.domain.uploadproduct.ProductDomain;

@Entity
@Table(name="SubImages")
public class SubImageDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6952162797843946684L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long subImageId;
	
	
	@Column(name="imagePath")
	private String imagePath;
	
	@ManyToOne
	@JoinColumn(name="product_Id")
	private ProductDomain productDomain;

	public long getSubImageId() {
		return subImageId;
	}

	public void setSubImageId(long subImageId) {
		this.subImageId = subImageId;
	}


	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ProductDomain getProductDomain() {
		return productDomain;
	}

	public void setProductDomain(ProductDomain productDomain) {
		this.productDomain = productDomain;
	}

}
