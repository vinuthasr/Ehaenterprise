/*package com.elephant.service.cart;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephant.dao.cart.CartDao;
import com.elephant.dao.cart.CartDaoRepository;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.domain.cart.CartDomain;
import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.model.cart.CartModel;
import com.elephant.model.cartitem.CartItemModel;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	CartDao cartDao;
	
	@Autowired
	CartDaoRepository cartDaoRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public CartModel createCart(CartModel cartModel) {
		
		try {
		CartDomain cartDomain=new CartDomain();
		BeanUtils.copyProperties(cartDomain, cartModel);
		 cartDao.createCart(cartDomain);
		return cartModel;
		}
		catch(Exception ex) {
		System.out.println("Exception in service cart"+ex);
		}
		return null;
	}

	
	@Override
	public void updateCart(CartModel cartModel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CartModel getCartById(long cartId) {
		CartDomain cartDomain=cartDaoRepository.getOne(cartId);
		CartModel cartModel=new CartModel();
		BeanUtils.copyProperties(cartDomain, cartModel);
		return cartModel;
	}

	@Override
	public void emptyCart(long cartId) {
		CartDomain cartDomain=cartDaoRepository.getOne(cartId);
		//List<CartItemDomain> cartItems=cartDomain.getCartItem();
		for(CartItemDomain Domain :cartDomain.getCartItem()) {
		//cartDaoRepository.delete(Domain);
		}
	}


	@Override
	public void deleteByCartId(long cartId) {
		 cartDaoRepository.deleteById(cartId);
	}

	@Override
	public double getGrandTotal(String email) {
		double grandtotal=0;
		CustomerDomain customerDomain=customerRepository.findByEmail(email);
		CartDomain cartDomain=cartDaoRepository.findByCartId(customerDomain.getCartDomain().getCartId());
		List<CartItemDomain> cartItemdomain=cartDomain.getCartItem();
		for(CartItemDomain cartItemDomain:cartItemdomain) {
			grandtotal+=cartItemDomain.getTotalprice();
		}
		cartDomain.setGrandtotal(grandtotal);
		cartDaoRepository.save(cartDomain);
		return grandtotal;
	}
	
}
*/