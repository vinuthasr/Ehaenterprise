package com.elephant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.elephant.constant.Constants;
import com.elephant.service.city.CityService;
import com.elephant.service.customer.CustomerService;
import com.elephant.service.state.StateService;

@SpringBootApplication
@EnableJpaRepositories


public class EhaEnterpriseApplication implements ApplicationRunner{
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	StateService stateService;
	
	@Autowired
	CityService cityService;

	
	public static void main(String[] args) {
		SpringApplication.run(EhaEnterpriseApplication.class, args);
		System.out.println("<---------------------BOOOOOOOOOOOOTED--------------------------->");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		customerService.addRole(Constants.ROLE_ADMIN);
		customerService.addRole(Constants.ROLE_USER);
		customerService.addAdmin("ehaadmin@gmail.com","12345678","Puneeth");		
		
		stateService.addState();
		cityService.addCity();
	}

}
