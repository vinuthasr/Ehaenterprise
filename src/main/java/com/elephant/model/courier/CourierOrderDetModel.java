package com.elephant.model.courier;

import java.io.Serializable;
import java.util.Date;

public class CourierOrderDetModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6305160829888963076L;
	
	private String courierOrderId;
	private String orderId;
	private String orderDetailId;
	private String wayBill;
	private String uploadWbn;
	private String sortCode;
	private String status;
	private Date createdDate;
	private Date modifiedDate;
    private String customerEmail;
    
	public String getCourierOrderId() {
		return courierOrderId;
	}
	public void setCourierOrderId(String courierOrderId) {
		this.courierOrderId = courierOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public String getWayBill() {
		return wayBill;
	}
	public void setWayBill(String wayBill) {
		this.wayBill = wayBill;
	}
	public String getUploadWbn() {
		return uploadWbn;
	}
	public void setUploadWbn(String uploadWbn) {
		this.uploadWbn = uploadWbn;
	}
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

}
