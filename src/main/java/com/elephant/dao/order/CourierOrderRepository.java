package com.elephant.dao.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.courier.CourOrderDetDomain;

public interface CourierOrderRepository extends JpaRepository<CourOrderDetDomain, String>{
	CourOrderDetDomain findByCourierOrderId(String courierOrderId);
}
