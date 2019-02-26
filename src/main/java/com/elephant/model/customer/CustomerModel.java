package com.elephant.model.customer;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.order.OrderDomain;
import com.elephant.model.address.AddressModel;
import com.elephant.model.cartitem.CartItemModel;
import com.elephant.model.invoice.InvoiceModel;
import com.elephant.model.order.OrderModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_DEFAULT)
public class CustomerModel implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5401649711068290932L;
	private long customersId;
	private String customerName;
	private String gender;
	private String email;
	private String password;
	private long mobileNumber;
	//private String confirmPassword;
	private String address;
	private String rolename;
	
	//private Date dateOfBirth;
	private boolean isActive;
	private long rollId;
	//private boolean isActiveUser;
	private String valitateCode;
	private List<CartItemModel> cartItemModel;
	private List<AddressModel> addressModel;
	//private CartModel cartModel;
	private List<OrderModel> orderModel;
	private List<InvoiceModel> invoiceModel;
	
	
	public List<CartItemModel> getCartItemModel() {
		return cartItemModel;
	}
	public void setCartItemModel(List<CartItemModel> cartItemModel) {
		this.cartItemModel = cartItemModel;
	}
	public List<InvoiceModel> getInvoiceModel() {
		return invoiceModel;
	}
	public void setInvoiceModel(List<InvoiceModel> invoiceModel) {
		this.invoiceModel = invoiceModel;
	}
	public List<OrderModel> getOrderModel() {
		return orderModel;
	}
	public void setOrderModel(List<OrderModel> orderModel) {
		this.orderModel = orderModel;
	}
	public List<AddressModel> getAddressModel() {
		return addressModel;
	}
	public void setAddressModel(List<AddressModel> addressModel) {
		this.addressModel = addressModel;
	}
	/*public CartModel getCartModel() {
		return cartModel;
	}
	public void setCartModel(CartModel cartModel) {
		this.cartModel = cartModel;
	}*/
	
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public long getCustomersId() {
		return customersId;
	}
	public void setCustomersId(long customersId) {
		this.customersId = customersId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
//	public String getConfirmPassword() {
//		return confirmPassword;
//	}
//	public void setConfirmPassword(String confirmPassword) {
//		this.confirmPassword = confirmPassword;
//	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public long getRollId() {
		return rollId;
	}
	public void setRollId(long rollId) {
		this.rollId = rollId;
	}
//	public boolean isActiveUser() {
//		return isActiveUser;
//	}
//	public void setActiveUser(boolean isActiveUser) {
//		this.isActiveUser = isActiveUser;
//	}
	public String getValitateCode() {
		return valitateCode;
	}
	public void setValitateCode(String valitateCode) {
		this.valitateCode = valitateCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
/*
	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 6955164599497677654L;
	
	private long customersId;
	private String customerName;
	private String gender;
	private String email;
	private String password;
	private String mobileNumber;
	private String confirmPassword;
	private String address;
	private String city;
	private String state;
	private String country;
	//private Date dateOfBirth;
	private boolean isActive;
	private long rollId;
	private int confirmValue;
	private String valitateCode;
	
	private CartModel cartModel;
	
	private List<AddressModel>  address1;
	
	public CartModel getCartModel() {
		return cartModel;
	}
	public void setCartModel(CartModel cartModel) {
		this.cartModel = cartModel;
	}
	
	public List<AddressModel> getAddress1() {
		return address1;
	}
	public void setAddress1(List<AddressModel> address1) {
		this.address1 = address1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getRollId() {
		return rollId;
	}
	public void setRollId(long rollId) {
		this.rollId = rollId;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	//	public Date getDateOfBirth() {
//		return dateOfBirth;
//	}
//	public void setDateOfBirth(Date dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
	public long getCustomersId() {
		return customersId;
	}
	public void setCustomersId(long customersId) {
		this.customersId = customersId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getConfirmValue() {
		return confirmValue;
	}
	public void setConfirmValue(int confirmValue) {
		this.confirmValue = confirmValue;
	}
	public String getValitateCode() {
		return valitateCode;
	}
	public void setValitateCode(String valitateCode) {
		this.valitateCode = valitateCode;
	}
	
	
	
*/	

}
