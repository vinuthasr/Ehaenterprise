package com.elephant.dao.cartitem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CartItemDaoImpl implements CartItemDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	@Override
	public void deleteCartItem(Long cartItemId) throws Exception {
		// TODO Auto-generated method stub
		try {
			String sql = "DELETE FROM cartitem WHERE cart_item_id = "+cartItemId;
			jdbcTemplate.execute(sql);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
