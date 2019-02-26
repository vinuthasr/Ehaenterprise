/*package com.elephant.controller.cart;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.domain.cart.CartDomain;
import com.elephant.model.cart.CartModel;
import com.elephant.response.Response;
import com.elephant.service.cart.CartService;

@RestController
@RequestMapping(value="/cart")
public class CartController {

	@Autowired
	CartService cartService;
	
	@RequestMapping(value="/add/", method=RequestMethod.POST, produces="application/json")
	public CartModel createCart(@RequestBody CartModel cartModel) {
		return cartService.createCart(cartModel);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT, produces="application/json")
	public void updateCart(@RequestBody CartModel cartModel) {
		cartService.updateCart(cartModel);
	}
	
	@RequestMapping(value="/get/{cartId}",method=RequestMethod.GET, produces="application/json")	
	public CartModel getCartById(@PathVariable(value="cartId") long cartId  ) {
		return cartService.getCartById(cartId);
	}
	
	@RequestMapping(value="/grandtotal/{email}",method=RequestMethod.GET, produces="application/json")	
	public double getGrandTotal(@RequestParam(value="email") String email) {
		return cartService.getGrandTotal(email);
		
	}

	@RequestMapping(value="/delete/{cartId}",method=RequestMethod.DELETE, produces="application/json")	
	public void deleteByCartId(@PathVariable(value="cartId") long cartId) {
		 cartService.deleteByCartId(cartId);
		
	}
	
	
	@RequestMapping(value="/empty/",method=RequestMethod.DELETE, produces="application/json")	
	public void emptyCart(long cartId) {
		 cartService.emptyCart(cartId);
	}
	
}
*/