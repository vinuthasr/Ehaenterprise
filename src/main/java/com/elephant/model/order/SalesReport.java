package com.elephant.model.order;

import java.io.Serializable;

public class SalesReport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4480114864334335576L;
	
	private String month;
	private int year;
	private int orderCount;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	
	
}
