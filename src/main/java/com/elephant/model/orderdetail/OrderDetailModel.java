package com.elephant.model.orderdetail;

import java.io.Serializable;

import com.elephant.model.order.OrderModel;

public class OrderDetailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1104181134290149460L;
	private long orderdetailId;
	private long productId;
	private String productSku;	
	private String productImagePath;
	private String productName;
	private String productQuantity;
	private double productAmount;
	private OrderModel orderModel;
	
	public String getProductSku() {
		return productSku;
	}
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	public String getProductImagePath() {
		return productImagePath;
	}
	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	
	public OrderModel getOrderModel() {
		return orderModel;
	}
	public void setOrderModel(OrderModel orderModel) {
		this.orderModel = orderModel;
	}
	public long getOrderdetailId() {
		return orderdetailId;
	}
	public void setOrderdetailId(long orderdetailId) {
		this.orderdetailId = orderdetailId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	public double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}
	
	
}
