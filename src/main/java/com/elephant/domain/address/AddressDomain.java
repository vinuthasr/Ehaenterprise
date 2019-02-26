package com.elephant.domain.address;

import java.io.Serializable;
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

import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.invoice.InvoiceDomain;
import com.elephant.domain.order.OrderDomain;

@Entity
@Table(name="address")
public class AddressDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4438933691687068650L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long addressId;
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="addressline1")
	private String addressline1;
	
	@Column(name="addressline2")
	private String addressline2;
	
	@Column(name="addressline3")
	private String addressline3;
	
	@Column(name="pincode")
	private String pincode;

	@Column(name="town")
	private String town;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	

	@ManyToOne
	@JoinColumn(name="customersId")
	private CustomerDomain customerDomain;
	
	@OneToMany(mappedBy="addressDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<OrderDomain> orderDomain;
	
	@OneToMany(mappedBy="addressDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<InvoiceDomain> invoiceDomain;
	
	public List<InvoiceDomain> getInvoiceDomain() {
		return invoiceDomain;
	}

	public void setInvoiceDomain(List<InvoiceDomain> invoiceDomain) {
		this.invoiceDomain = invoiceDomain;
	}

	public List<OrderDomain> getOrderDomain() {
		return orderDomain;
	}

	public void setOrderDomain(List<OrderDomain> orderDomain) {
		this.orderDomain = orderDomain;
	}

	public CustomerDomain getCustomerDomain() {
		return customerDomain;
	}

	public void setCustomerDomain(CustomerDomain customerDomain) {
		this.customerDomain = customerDomain;
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


		
	
		
	
}
