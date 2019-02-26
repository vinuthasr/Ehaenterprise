package com.elephant.domain.cartitem;

import java.io.Serializable;

import javax.persistence.*;

import com.elephant.domain.customer.CustomerDomain;
//import com.elephant.domain.cart.CartDomain;
import com.elephant.domain.uploadproduct.ProductDomain;

@Entity
@Table(name="cartitem")
public class CartItemDomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8667129081632539755L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cartItemId")
	private long cartItemId;
	
	@ManyToOne
	private ProductDomain product;
	
	@Column(name="quantity")
	private int quantity;
	
	/*@Column(name="totalprice")
	private float totalprice;
	*/
	@ManyToOne
	@JoinColumn(name="customersId")
	private CustomerDomain  customerDomain;
	
	public CustomerDomain getCustomerDomain() {
		return customerDomain;
	}

	public void setCustomerDomain(CustomerDomain customerDomain) {
		this.customerDomain = customerDomain;
	}

	/*@ManyToOne
	@JoinColumn(name="cartId")
	private CartDomain  cartDomain;
	
	
	public CartDomain getCartDomain() {
		return cartDomain;
	}

	public void setCartDomain(CartDomain cartDomain) {
		this.cartDomain = cartDomain;
	}
*/
	public long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public ProductDomain getProduct() {
		return product;
	}

	public void setProduct(ProductDomain product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/*public float getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}*/

	
	
}
