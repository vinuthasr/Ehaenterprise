package com.elephant.model.uploadproduct;

import java.io.Serializable;
import java.util.List;

import com.elephant.model.subimagemodel.SubImageModel;

public class ProductModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId;
	private String sku;
	private Float discount;
	private Double price;
	private Long inStock;
	private double cp;
	private int quantity;
	private boolean isActive;
	private String uploadDate;
	private String modifiedDate;
	private String materialType;
	private String collectionDesc;
	private String colors;
	private String fabricPurity;
	private String pattern;
	private String border;
	private String borderType;
	private String zariType;
	private Double length;
	private String blouseColor;
	private Double blouseLength;
    private String occassion;
    private String mainImageUrl;
    private String headerDesc;
    private String categoryName;
    
    private List<SubImageModel> subImageList;
    
   // @Convert(converter = ImageListConverter.class)
	
	
	public Long getInStock() {
		return inStock;
	}
	public void setInStock(Long inStock) {
		this.inStock = inStock;
	}
	public Float getDiscount() {
		return discount;
	}
	public Double getPrice() {
		return price;
	}
	
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getMainImageUrl() {
		return mainImageUrl;
	}
	
	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}
	
	public String getProductId() {
		return productId;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
		public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getCollectionDesc() {
		return collectionDesc;
	}
	public void setCollectionDesc(String collectionDesc) {
		this.collectionDesc = collectionDesc;
	}

	
	
	public String getFabricPurity() {
		return fabricPurity;
	}
	public void setFabricPurity(String fabricPurity) {
		this.fabricPurity = fabricPurity;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getBorderType() {
		return borderType;
	}
	public void setBorderType(String borderType) {
		this.borderType = borderType;
	}
	public String getZariType() {
		return zariType;
	}
	public void setZariType(String zariType) {
		this.zariType = zariType;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	
	public String getBlouseColor() {
		return blouseColor;
	}
	public void setBlouseColor(String blouseColor) {
		this.blouseColor = blouseColor;
	}
	
	public Double getBlouseLength() {
		return blouseLength;
	}
	public void setBlouseLength(Double blouseLength) {
		this.blouseLength = blouseLength;
	}
	public String getOccassion() {
		return occassion;
	}
	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}
	public double getCp() {
		return cp;
	}
	public void setCp(double cp) {
		this.cp = cp;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public List<SubImageModel> getSubImageList() {
		return subImageList;
	}
	public void setSubImageList(List<SubImageModel> subImageList) {
		this.subImageList = subImageList;
	}
	public String getHeaderDesc() {
		return headerDesc;
	}
	public void setHeaderDesc(String headerDesc) {
		this.headerDesc = headerDesc;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}
	
	
	
	
	
	
	