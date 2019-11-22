package com.elephant.domain.orderdetail;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.elephant.domain.order.OrderDomain;


@Entity
@Table(name="orderdetail")
public class OrderDetailDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7060565072350058663L;

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private String orderdetailId;
	
	/*@Column(name="productId")
	private long productId;*/
	
	@Column(name="productSku")
	private String productSku;	
	
	
	@Column(name="productImagePath")
	private String productImagePath;
	
	@Column(name="productName")
	private String productName;
	
	@Column(name="productQuantity")
	private int productQuantity;
	
	@Column(name="productAmount")
	private double productAmount;
	
	@Column(name="status")
	private String status;
	
	@Column(name="statusDate")
	private Date statusDate;
	
	@ManyToOne
	@JoinColumn(name="orderId")
	private OrderDomain orderDomain;
	
//	@OneToOne(mappedBy="courierOrderDetails")
//	private CourOrderDetDomain courOrderDetDomain;
//	
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
	
	
	public OrderDomain getOrderDomain() {
		return orderDomain;
	}
	public void setOrderDomain(OrderDomain orderDomain) {
		this.orderDomain = orderDomain;
	}
	
	public String getOrderdetailId() {
		return orderdetailId;
	}
	public void setOrderdetailId(String orderdetailId) {
		this.orderdetailId = orderdetailId;
	}
	/*public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}*/
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}
//	public CourOrderDetDomain getCourOrderDetDomain() {
//		return courOrderDetDomain;
//	}
//	public void setCourOrderDetDomain(CourOrderDetDomain courOrderDetDomain) {
//		this.courOrderDetDomain = courOrderDetDomain;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
}
