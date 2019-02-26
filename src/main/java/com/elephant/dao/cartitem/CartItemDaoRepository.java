package com.elephant.dao.cartitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;

public interface CartItemDaoRepository extends JpaRepository<CartItemDomain, Long>{

	//public List<CartItemDomain> findCartItemsByCustomer(CustomerDomain customerDomain);

}
