package com.elephant.service.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.elephant.dao.city.CityRepository;
import com.elephant.domain.city.CityDomain;

@Service
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
		String sql = "INSERT INTO e_commerce.city(city_name,state_id)\r\n" + 
				"VALUES('Bagalkot','17'),\r\n" + 
				"('Bangalore','17'),\r\n" + 
				"('Bangalore Rural','17'),\r\n" + 
				"('Belgaum','17'),\r\n" + 
				"('Bellary','17'),\r\n" + 
				"('Bhatkal','17'),\r\n" + 
				"('Bidar', '17'),\r\n" + 
				"('Bijapur','17'),\r\n" + 
				"('Chamrajnagar','17'),\r\n" + 
				"('Chickmagalur','17'),\r\n" + 
				"('Chikballapur','17'),\r\n" + 
				"('Chitradurga', '17'),\r\n" + 
				"('Dakshina Kannada','17'),\r\n" + 
				"('Davanagere','17'),\r\n" + 
				"('Dharwad','17'),\r\n" + 
				"('Gadag','17'),\r\n" + 
				"('Gulbarga','17'),\r\n" + 
				"('Hampi','17'),\r\n" + 
				"('Hassan','17'),\r\n" + 
				"('Haveri','17'),\r\n" + 
				"('Hospet','17'),\r\n" + 
				"('Karwar','17'),\r\n" + 
				"('Kodagu','17'),\r\n" + 
				"('Kolar','17'),\r\n" + 
				"('Koppal','17'),\r\n" + 
				"('Madikeri','17'),\r\n" + 
				"('Mandya','17'),\r\n" + 
				"('Mangalore','17'),\r\n" + 
				"('Manipal','17'),\r\n" + 
				"('Mysore','17'),\r\n" + 
				"('Raichur','17'),\r\n" + 
				"('Shimoga','17'),\r\n" + 
				"('Sirsi', '17'),\r\n" + 
				"('Sringeri','17'),\r\n" + 
				"('Srirangapatna','17'),\r\n" + 
				"('Tumkur','17'),\r\n" + 
				"('Udupi','17'),\r\n" + 
				"('Uttara Kannada','17')";
		
		return sql;
	}
	
	@Override
	public List<CityDomain> getCityNames(String stateName) {
		List<CityDomain> cityList = null;
		try {
			String sql = "select c.city_id,c.city_name from city c, state s where c.state_id = s.state_id and s.state_name = ?";
			cityList = jdbcTemplate.query(sql, new Object[] { stateName }, new BeanPropertyRowMapper<CityDomain>(CityDomain.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cityList;
	}

}
