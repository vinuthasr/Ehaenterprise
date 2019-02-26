package com.elephant.domain.uploadproduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import com.elephant.controller.imageList.ImageListConverter;
import com.elephant.domain.category.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="uploadproduct")
public class ProductDomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="productId")
	private String productId;
	
	@Column(name="calPrice")
	private double cp;
	
	
	@Column(name="sku",unique=true)
	private String sku;
		
	@Column(name="discount")
	private Float discount;
	
	@Column(name="price")
	private Double price;
		
	@Column(name="inStock")
	private Long inStock;
	
	@Column(name="quantity")
	private int quantity;
	
	
	
	
	@Column(name="isActive")
	private boolean isActive;
	
	@Column(name="uploadDate")
	private String uploadDate;
	
	@Column(name="modifiedDate")
	private String modifiedDate;
	
	@Column(name="materialType")
	private String materialType;
	
	@Column(name="collectionDesc",columnDefinition="TEXT",length=800)
	private String collectionDesc;
	
	@Column(name="colors")
    private String colors;
	
	@Column(name="occassion")
    private String occassion;
	
	@JsonBackReference@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY,optional=true)
    @JoinColumn(name="categoryId")//,referencedColumnName = "categoryName",insertable=true)
	private Category category;
	private String fabricPurity;
	private String pattern;
	private String border;
	private String borderType;
	private String zariType;
	private Double length;
	private String blouse;
	private String blouseColor;
	private Double blouseLength;
	
	private String mainImageUrl;
	@Convert(converter = ImageListConverter.class)
	//private List<UploadProductDomain> otherImageUrls=new ArrayList<>();
   	private List<String> otherImageUrls=new ArrayList<>();
		
	public List<String> getOtherImageUrls() {
		return otherImageUrls;
	}
	
	public Long getInStock() {
		return inStock;
	}
	public void setInStock(Long inStock) {
		this.inStock = inStock;
	}
	public void setOtherImageUrls(List<String> otherImageUrls) {
		this.otherImageUrls = otherImageUrls;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getMainImageUrl() {
		return mainImageUrl;
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

		public String getOccassion() {
		return occassion;
	}
	public void setOccassion(String occassion) {
		this.occassion = occassion;
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
	public String getBlouse() {
		return blouse;
	}
	public void setBlouse(String blouse) {
		this.blouse = blouse;
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
	
}