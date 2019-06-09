package com.elephant.dao.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.payment.PaymentDomain;

public interface PaymentDao extends JpaRepository<PaymentDomain,Integer >{
	PaymentDomain findByTxnId(String txnId);

}
