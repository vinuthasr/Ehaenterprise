package com.elephant.dao.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elephant.domain.payment.PUMPaymentDomain;

@Repository
public interface PUMRepo extends  JpaRepository<PUMPaymentDomain,Integer >{
	PUMPaymentDomain findByTxnId(String txnId);
}
