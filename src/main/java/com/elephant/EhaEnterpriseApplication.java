package com.elephant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.ui.Model;

import com.elephant.constant.Constants;
import com.elephant.model.customer.CustomerModel;
import com.elephant.service.customer.CustomerService;

@SpringBootApplication
@EnableJpaRepositories


public class EhaEnterpriseApplication implements ApplicationRunner{
	
	@Autowired
	CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(EhaEnterpriseApplication.class, args);
		System.out.println("<---------------------BOOOOOOOOOOOOTED--------------------------->");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		customerService.addRole(Constants.ROLE_ADMIN);
		customerService.addRole(Constants.ROLE_USER);
		customerService.addAdmin("ehaadmin@gmail.com","12345678","Puneeth");		
		
	}

}
