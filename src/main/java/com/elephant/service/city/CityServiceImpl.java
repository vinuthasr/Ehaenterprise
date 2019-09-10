package com.elephant.service.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.elephant.dao.city.CityRepository;
import com.elephant.domain.city.CityDomain;

public class CityServiceImpl implements CityService{

	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void addCity() {
		// TODO Auto-generated method stub
		List<CityDomain> cityList = cityRepository.findAll();
		if(cityList.size() == 0) {
			try {
				String sql = insertKarnatakaValues();
				
				int res = jdbcTemplate.update(sql);
				if (res > 1) {
					System.out.println("success");
				} else {
					System.out.println("failure");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private String insertKarnatakaValues() {
		String sql = null;
		
		return sql;
	}
	@Override
	public List<CityDomain> getCityNames(String stateName) {
		// TODO Auto-generated method stub
		return null;
	}

}
