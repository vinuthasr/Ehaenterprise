package com.elephant.domain.customer;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

//import com.elephant.domain.roles.Role;
import org.springframework.format.annotation.NumberFormat;

import com.elephant.constant.StatusCode;
import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.cartitem.CartItemDomain;
//import com.elephant.domain.cart.CartDomain;
import com.elephant.domain.invoice.InvoiceDomain;
import com.elephant.domain.order.OrderDomain;
import com.elephant.domain.roles.Role;

@Entity
@Table(name="customers")
public class CustomerDomain implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4662026020580548585L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customersId")
	
	private long customersId;
	
	@Column(name="customerName")
	private String customerName;

	@Column(name="gender")
	private String gender;
	
	
	
	@Email
	@Column(name="email",unique=true)
	private String email;
	
	@Column(name="password")
	private String password;
	
	@NumberFormat
	@Column(name="mobileNumber")
	private long mobileNumber;
	
//	@Column(name="confirmPassword")
//	private String confirmPassword;
	 
	
	 private Date expiryDate;
	 
	 @ManyToMany(fetch=FetchType.LAZY)
	 @JoinTable(name = "user_roles", 
 	joinColumns = @JoinColumn(name = "customer_id"), 
 	inverseJoinColumns = @JoinColumn(name = "role_id"))
 //private Set<Role> roles = new HashSet<>();
	 
	// @OneToMany(mappedBy="customerDomain",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
		//@JoinTable(name = "USER_ROLES", joinColumns={
				//@JoinColumn(name = "USER_ID", referencedColumnName ="customersId") }, inverseJoinColumns = {
					//@JoinColumn(name = "ROLE_NAME", referencedColumnName = "name") })
		private List<Role> roles;
		
	
	
	
	@OneToMany(mappedBy="customerDomain", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY )
	private List<AddressDomain> addressDomain;
	

	/*//@OneToOne(mappedBy="customer", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY )	
	@OneToOne
	@JoinColumn(name="cartId")
	private CartDomain cartDomain;*/
	
	@OneToMany(mappedBy="customerDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<CartItemDomain> cartItemDomain;

	

	@OneToMany(mappedBy="customerDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<OrderDomain> orderDomain;
	
	@OneToMany(mappedBy="customerDomain", cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<InvoiceDomain> invoiceDomain;
	

	public List<CartItemDomain> getCartItemDomain() {
		return cartItemDomain;
	}
	public void setCartItemDomain(List<CartItemDomain> cartItemDomain) {
		this.cartItemDomain = cartItemDomain;
	}
	
	public List<AddressDomain> getAddressDomain() {
		return addressDomain;
	}
	public void setAddressDomain(List<AddressDomain> addressDomain) {
		this.addressDomain = addressDomain;
	}
	
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
	/*public CartDomain getCartDomain() {
		return cartDomain;
	}
	public void setCartDomain(CartDomain cartDomain) {
		this.cartDomain = cartDomain;
	}*/
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private boolean isActive;
	
//	private boolean isActiveUser;
	
	@Column(name="valitateCode")
	private String valitateCode;
	
//	@Temporal(TemporalType.DATE)
//	@Column(name="dateOfBirth")
//	private Date dateOfBirth;
//	
	
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
	
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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
	public void setExpiryDate(int minutes){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }
	  public boolean isExpired() {
	        return new Date().after(this.expiryDate);
	    }
	  
	  public CustomerDomain(String email, String name, String password) {
			this.email = email;
			this.customerName = name;
			this.password = password;
		}
	public CustomerDomain() {
		// TODO Auto-generated constructor stub
	}
	
	

	
	

	
	

}
