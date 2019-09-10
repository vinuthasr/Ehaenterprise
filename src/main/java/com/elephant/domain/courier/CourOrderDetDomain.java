package com.elephant.domain.courier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courierOrderDetails")
public class CourOrderDetDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8442185426614276238L;
	
	@Id
	@Column(name="courierOrderId", unique = true)
	private String courierOrderId;
	
	@Column(name="orderId")
	private String orderId;
	
	@Column(name="orderDetailId")
	private String orderDetailId;
	
	@Column(name="wayBill")
	private String wayBill;
	
	@Column(name="uploadWbn")
	private String uploadWbn;
	
	@Column(name="sortCode")
	private String sortCode;
	
	@Column(name="status")
	private String status;
	
	@Column(name="createdDate")
	private Date createdDate;
	
	@Column(name="modifiedDate")
	private Date modifiedDate;
	
    @Column(name="customerEmail")
    private String customerEmail;

//    @OneToOne
//	@JoinColumn(name="orderdetailId")
//	private OrderDetailDomain orderDetailDomains;
    
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

//	public OrderDetailDomain getOrderDetailDomains() {
//		return orderDetailDomains;
//	}
//
//	public void setOrderDetailDomains(OrderDetailDomain orderDetailDomains) {
//		this.orderDetailDomains = orderDetailDomains;
//	}

}
