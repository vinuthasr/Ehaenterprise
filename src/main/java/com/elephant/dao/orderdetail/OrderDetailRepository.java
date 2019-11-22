package com.elephant.dao.orderdetail;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.orderdetail.OrderDetailDomain;

public interface OrderDetailRepository extends JpaRepository<OrderDetailDomain, Long> {

	//public OrderDfindAllByOrderId(long orderId);
	
	public OrderDetailDomain getOrderDetailByOrderdetailId(String orderdetailId);
}
