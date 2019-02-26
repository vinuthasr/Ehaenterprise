package com.elephant.dao.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.order.OrderDomain;

public interface OrderDaoRepository extends JpaRepository<OrderDomain, Long> {

	public List<OrderDomain> findAllByOrderDate(Date orderDate);
	
	public List<OrderDomain> findAllByOrderDateBetween(Date fromDate, Date toDate);
	
	public OrderDomain findByOrderId(long orderId);
	
	public OrderDomain findByOrderNumber(String OrderNumber);
	
}


