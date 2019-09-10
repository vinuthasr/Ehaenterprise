package com.elephant.domain.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.orderdetail.OrderDetailDomain;

@Entity
@Table(name = "orders")
public class OrderDomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5881511600024713673L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long orderId;

	@Column(name="orderDate")
	@Temporal(TemporalType.DATE)
    private Date orderDate;
	
	@Column(name="orderNumber", unique=true)
	private String orderNumber;
	
	
	@Column(name="orderPrice")
    private double orderPrice;
	
	@Column(name="orderStatus")
	private String orderStatus;
	
	@Column(name="paymentMode")
	private String paymentMode;
	 
	@Column(name="tansactionId")
	private String transactionId;
	
	@Column(name="customerName")
	private String customerName;
	
	
	@Column(name="customerEmail")
	private String customerEmail;
	
	
	@Column(name="customerMobileNumber")
	private long customerMobileNumber;
	
	@ManyToOne
	@JoinColumn(name="customersId")
    private CustomerDomain customerDomain;
	
	@ManyToOne
	@JoinColumn(name="addressId")
	private AddressDomain addressDomain;
	
	public long getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(long customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public AddressDomain getAddressDomain() {
		return addressDomain;
	}
	public void setAddressDomain(AddressDomain addressDomain) {
		this.addressDomain = addressDomain;
	}
	@OneToMany(mappedBy="orderDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<OrderDetailDomain> orderDetailDomain;
	
	 public List<OrderDetailDomain> getOrderDetailDomain() {
		return orderDetailDomain;
	}
	public void setOrderDetailDomain(List<OrderDetailDomain> orderDetailDomain) {
		this.orderDetailDomain = orderDetailDomain;
	}
	public String getOrderNumber() {
			return orderNumber;
		}
		public void setOrderNumber(String orderNumber) {
			this.orderNumber = orderNumber;
		}
	
	public CustomerDomain getCustomerDomain() {
		return customerDomain;
	}
	public void setCustomerDomain(CustomerDomain customerDomain) {
		this.customerDomain = customerDomain;
	}
	
	/*@OneToMany
    private List<CartItemDomain> cartItemDomain;*/
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	}
	/*public List<CartItemDomain> getCartItemDomain() {
		return cartItemDomain;
	}
	public void setCartItemDomain(List<CartItemDomain> cartItemDomain) {
		this.cartItemDomain = cartItemDomain;
	}*/
	


