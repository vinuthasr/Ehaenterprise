/*package com.elephant.dao.cart;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.elephant.constant.StatusCode;
import com.elephant.domain.cart.CartDomain;
import com.elephant.model.cart.CartModel;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;

@Repository
@Transactional
public class CartDaoImpl implements CartDao{

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CartDaoRepository cartDaoRepository;
	
	@Override
	public Response createCart(CartDomain cartDomain) {
		Response response=CommonUtils.getResponseObject("Creating cart");
		try {
		entityManager.persist(cartDomain);
		response.setStatus(StatusCode.SUCCESS.name());
		response.getStatus();
		}
		catch(Exception ex) {
			System.out.println("exception in creating cart"+ex);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(ex.getMessage());
		}
		return response;
	}
	
	
	@Override
	public CartDomain createCart(CartDomain cartDomain) {	
		return cartDaoRepository.save(cartDomain);
	}

}
*/