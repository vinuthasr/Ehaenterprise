package com.elephant.service.city;

import java.util.List;

import com.elephant.domain.city.CityDomain;

public interface CityService {
	public void addCity();
	public List<CityDomain> getCityNames(String stateName);
}
