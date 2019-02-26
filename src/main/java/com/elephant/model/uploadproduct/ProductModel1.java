package com.elephant.model.uploadproduct;

import java.util.List;

public class ProductModel1 {
	
	private List<String> categoryName;
	private List<String> colors;
	private Float discount;
	private Double length;
	private double min; 
	private double max; 
	
	private String materialType;
	private String fabricPurity;
	private String pattern;
	private String border; 
	private String borderType;
	private String zariType;
	private String blouse;
	private String blouseColor;
	private Double blouseLength;

	
	public List<String> getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(List<String> categoryName) {
		this.categoryName = categoryName;
	}
	 
	
	public List<String> getColors() {
		return colors;
	}
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	
	
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
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

}
