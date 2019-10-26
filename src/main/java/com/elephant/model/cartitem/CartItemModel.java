package com.elephant.model.cartitem;



import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.model.customer.CustomerModel;
//import com.elephant.model.cart.CartModel;
import com.elephant.model.uploadproduct.ProductModel;

public class CartItemModel {

	private long cartItemId;
	private String Sku;
	private int quantity;
	private ProductModel product;
	
	
	public long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public String getSku() {
		return Sku;
	}
	public void setSku(String sku) {
		Sku = sku;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	
}
