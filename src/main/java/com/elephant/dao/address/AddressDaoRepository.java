package com.elephant.dao.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.address.AddressDomain;

public interface AddressDaoRepository  extends JpaRepository<AddressDomain, Long>{

	public AddressDomain findByAddressId(long addressId);
	public List<AddressDomain> findAllByCountry(String country);
	 
}
