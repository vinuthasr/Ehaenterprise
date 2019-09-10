package com.elephant.service.state;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.elephant.dao.state.StateRepository;
import com.elephant.domain.state.State;

@Service
public class StateServiceImpl implements StateService{
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void addState() {
		// TODO Auto-generated method stub
		List<State> stateList = stateRepository.findAll();
		if(stateList.size() == 0) {
			try {
				String sql = "INSERT INTO e_commerce.state (state_name, state_code) \r\n" + 
						"VALUES('Andaman and Nicobar','AN'),\r\n" + 
						"('Andhra Pradesh','AP'),\r\n" + 
						"('Arunachal Pradesh','AR'),\r\n" + 
						"('Assam', 'AS'),\r\n" + 
						"('Bihar','BH'),\r\n" + 
						"('Chandigarh','CH'),\r\n" + 
						"('Chhattisgarh','CG'),\r\n" + 
						"('Dadra & Nagar Haveli','DN'),\r\n" + 
						"('Daman & Diu','DD'),\r\n" + 
						"('Delhi','DL'),\r\n" + 
						"('Goa','GO'),\r\n" + 
						"('Gujarat','GU'),\r\n" + 
						"('Haryana','HR'),\r\n" + 
						"('Himachal Pradesh','HP'),\r\n" + 
						"('Jammu & Kashmir', 'JK'),\r\n" + 
						"('Jharkhand','JH'),\r\n" + 
						"('Karnataka','KR'),\r\n" + 
						"('Kerala','KL'),\r\n" + 
						"('Lakshadweep','LD'),\r\n" + 
						"('Madhya Pradesh','MP'),\r\n" + 
						"('Maharashtra','MH'),\r\n" + 
						"('Manipur','MN'),\r\n" + 
						"('Meghalaya','ML'),\r\n" + 
						"('Mizoram','MM'),\r\n" + 
						"('Nagaland','NL'),\r\n" + 
						"('Orissa','OR'),\r\n" + 
						"('Pondicherry','PC'),\r\n" + 
						"('Punjab','PJ'),\r\n" + 
						"('Rajasthan','RJ'),\r\n" + 
						"('Sikkim','SK'),\r\n" + 
						"('Tamil Nadu','TN'),\r\n" + 
						"('Tripura','TR'),\r\n" + 
						"('Uttar Pradesh','UP'),\r\n" + 
						"('Uttaranchal','UT'),\r\n" + 
						"('West Bengal','WB')";
				
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

	@Override
	public List<State> getState() {
		List<State> stateList = null;
		try {
			stateList = stateRepository.findAll();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return stateList;
	}

	
}
