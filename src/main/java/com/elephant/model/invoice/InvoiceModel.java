package com.elephant.model.invoice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.elephant.model.address.AddressModel;
import com.elephant.model.customer.CustomerModel;
import com.elephant.model.invoicedetails.InvoiceDetailsModel;

public class InvoiceModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1586947699720369098L;

	private long invoiceId;
	private Date invoiceDate;
	private String invoiceNumber;
	private String orderNumber;
	private double orderTotal;
	private String customerName;
	private String customerEmail;
	private String customerMobileNumber;
	private CustomerModel customerModel;
	
	private List<InvoiceDetailsModel> invoiceDetailsModel;
	
	private AddressModel addressModel;
	
	public AddressModel getAddressModel() {
		return addressModel;
	}
	public void setAddressModel(AddressModel addressModel) {
		this.addressModel = addressModel;
	}
	/*Getters and Setters*/
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public CustomerModel getCustomerModel() {
		return customerModel;
	}
	public void setCustomerModel(CustomerModel customerModel) {
		this.customerModel = customerModel;
	}
	
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public double getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	public List<InvoiceDetailsModel> getInvoiceDetailsModel() {
		return invoiceDetailsModel;
	}
	public void setInvoiceDetailsModel(List<InvoiceDetailsModel> invoiceDetailsModel) {
		this.invoiceDetailsModel = invoiceDetailsModel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
