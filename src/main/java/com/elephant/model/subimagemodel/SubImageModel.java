package com.elephant.model.subimagemodel;

import java.io.Serializable;

import com.elephant.model.image.ImageModel;
import com.elephant.model.uploadproduct.ProductModel;

public class SubImageModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6080525859292977053L;
	
	private long subImageId;
	private String imagePath;
	private ProductModel productModel;
	
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
	public ProductModel getProductModel() {
		return productModel;
	}
	public void setProductModel(ProductModel productModel) {
		this.productModel = productModel;
	}

}
