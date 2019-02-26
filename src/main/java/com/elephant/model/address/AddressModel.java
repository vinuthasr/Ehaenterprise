package com.elephant.model.address;

import java.io.Serializable;

import com.elephant.model.customer.CustomerModel;
import com.elephant.model.invoice.InvoiceModel;
import com.elephant.model.order.OrderModel;

public class AddressModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7906428314279821517L;
	private long addressId;
	private String fullname;
	private String addressline1;
	private String addressline2;
	private String addressline3;	
	private String pincode;
	private String town;
	private String city;
	private String state;
	private String country;
	private CustomerModel customerModel;
	private OrderModel orderModel;
	private InvoiceModel invoiceModel;
	
	public InvoiceModel getInvoiceModel() {
		return invoiceModel;
	}
	public void setInvoiceModel(InvoiceModel invoiceModel) {
		this.invoiceModel = invoiceModel;
	}
	public OrderModel getOrderModel() {
		return orderModel;
	}
	public void setOrderModel(OrderModel orderModel) {
		this.orderModel = orderModel;
	}
	public CustomerModel getCustomerModel() {
		return customerModel;
	}
	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}
	public String getAddressline3() {
		return addressline3;
	}
	public void setAddressline3(String addressline3) {
		this.addressline3 = addressline3;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
		
	}
