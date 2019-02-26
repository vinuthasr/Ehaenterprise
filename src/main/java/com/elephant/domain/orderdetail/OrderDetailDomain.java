package com.elephant.domain.orderdetail;

import java.io.Serializable;

import javax.persistence.*;

import com.elephant.domain.order.OrderDomain;


@Entity
@Table(name="orderdetail")
public class OrderDetailDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7060565072350058663L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long orderdetailId;
	
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
	
	@ManyToOne
	@JoinColumn(name="orderId")
	private OrderDomain orderDomain;
	
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
	public long getOrderdetailId() {
		return orderdetailId;
	}
	public void setOrderdetailId(long orderdetailId) {
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
	
}
