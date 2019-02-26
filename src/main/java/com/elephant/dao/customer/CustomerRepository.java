
package com.elephant.dao.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.customer.CustomerDomain;

public interface CustomerRepository extends JpaRepository<CustomerDomain, Long> {

	
	CustomerDomain findByEmail(String email);
    
}
