package com.elephant.dao.city;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.city.CityDomain;

public interface CityRepository extends JpaRepository<CityDomain, Long>{

}
