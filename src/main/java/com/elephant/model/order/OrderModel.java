package com.elephant.model.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.elephant.domain.orderdetail.OrderDetailDomain;
import com.elephant.model.address.AddressModel;
import com.elephant.model.cartitem.CartItemModel;
import com.elephant.model.customer.CustomerModel;
import com.elephant.model.orderdetail.OrderDetailModel;
//import com.elephant.model.orderstatus.OrderStatusModel;

public class OrderModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8419806169469663605L;
	private long orderId;
    private Date orderDate;
	private String orderNumber;
    private double orderPrice;
	private String customerName;
	private String customerEmail;
	private long customerMobileNumber;
	private String orderStatus;
    private String paymentMode;
	private String transactionId;
	private CustomerModel customerModel;
    private List<OrderDetailModel> orderDetailModel;
    private AddressModel addressModel;
    
    public long getCustomerMobileNumber() {
		return customerMobileNumber;
	}
	public void setCustomerMobileNumber(long customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
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
	/*private OrderStatusModel orderStatusModel;
	
	public OrderStatusModel getOrderStatusModel() {
		return orderStatusModel;
	}
	public void setOrderStatusModel(OrderStatusModel orderStatusModel) {
		this.orderStatusModel = orderStatusModel;
	}*/
    
    public AddressModel getAddressModel() {
		return addressModel;
	}
	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
	}
	public List<OrderDetailModel> getOrderDetailModel() {
		return orderDetailModel;
	}
	public void setOrderDetailModel(List<OrderDetailModel> orderDetailModel) {
		this.orderDetailModel = orderDetailModel;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
    
    /*private List<CartItemModel> cartItemModel;

    public List<CartItemModel> getCartItemModel() {
		return cartItemModel;
	}
	public void setCartItemModel(List<CartItemModel> cartItemModel) {
		this.cartItemModel = cartItemModel;
	}*/
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
	public CustomerModel getCustomerModel() {
		return customerModel;
	}
	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
