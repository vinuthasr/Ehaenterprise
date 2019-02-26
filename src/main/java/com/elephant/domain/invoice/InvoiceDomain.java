package com.elephant.domain.invoice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.invoicedetails.InvoiceDetailsDomain;

@Entity
@Table(name="invoice")
public class InvoiceDomain  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -175124091208443860L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long invoiceId;
	
	@Column(name="invoiceDate")
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	
	@Column(name="invoiceNumber")
	private String invoiceNumber;
	
	@Column(name="orderNumber")
	private String orderNumber;
	
	@Column(name="orderTotal")
	private double orderTotal;
	
	@Column(name="customerName")
	private String customerName;
	
	
	@ManyToOne
	@JoinColumn(name="customersId")
	private CustomerDomain customerDomain;
	
	@OneToMany(mappedBy="invoiceDomain",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<InvoiceDetailsDomain> invoiceDetailsDomain;
	
	@ManyToOne
	@JoinColumn(name="addressId")
	private AddressDomain addressDomain;

	public AddressDomain getAddressDomain() {
		return addressDomain;
	}

	public void setAddressDomain(AddressDomain addressDomain) {
		this.addressDomain = addressDomain;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public CustomerDomain getCustomerDomain() {
		return customerDomain;
	}

	public void setCustomerDomain(CustomerDomain customerDomain) {
		this.customerDomain = customerDomain;
	}

	public List<InvoiceDetailsDomain> getInvoiceDetailsDomain() {
		return invoiceDetailsDomain;
	}

	public void setInvoiceDetailsDomain(List<InvoiceDetailsDomain> invoiceDetailsDomain) {
		this.invoiceDetailsDomain = invoiceDetailsDomain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

		
	
}
