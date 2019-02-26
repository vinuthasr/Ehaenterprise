/*package com.elephant.domain.cart;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;

@Entity
@Table(name="cart")
public class CartDomain implements Serializable {

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 2836306811031654693L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cartId")
	private long cartId;
	
	@Column(name="grandtotal")
	private double grandtotal;
	
	@OneToMany(mappedBy="cartDomain", cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
	private List<CartItemDomain> cartItem;
	
	@OneToOne
	@JoinColumn(name="customersId")
	private CustomerDomain customer;

	
	public CustomerDomain getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDomain customer) {
		this.customer = customer;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public double getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(double grandtotal) {
		this.grandtotal = grandtotal;
	}

	public List<CartItemDomain> getCartItem() {
		return cartItem;
	}

	public void setCartItem(List<CartItemDomain> cartItem) {
		this.cartItem = cartItem;
	}
		
}
*/