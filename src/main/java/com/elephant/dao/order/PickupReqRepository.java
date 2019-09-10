package com.elephant.dao.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.courier.PickupRequestDomain;

public interface PickupReqRepository extends JpaRepository<PickupRequestDomain, Long>{
	
	public List<PickupRequestDomain> findAllByPickupDateDtBetween(Date fromDate, Date toDate);
	
}
