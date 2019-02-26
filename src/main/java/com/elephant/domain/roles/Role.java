package com.elephant.domain.roles;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.elephant.domain.customer.CustomerDomain;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	private String name;
	
	//@ManyToMany(mappedBy = "roles",fetch=FetchType.LAZY)
	//@ManyToOne
	//@ManyToMany
	//@JoinColumn(name="customersId")
    //private CustomerDomain customerDomain;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

//	public Role(String name,  CustomerDomain customerDomain) {
//		this.name = name;
//		this.customerDomain = customerDomain;
//	}

//	public CustomerDomain getCustomerDomain() {
//		return customerDomain;
//	}
//
//	public void setCustomerDomain(CustomerDomain customerDomain) {
//		this.customerDomain = customerDomain;
//	}

	public Role() {
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

//	public Role(String name, CustomerDomain customerDomain) {
//		this.name = name;
//		this.customerDomain=customerDomain;
//		
//	}

}
