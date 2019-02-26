package com.elephant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EhaEnterpriseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhaEnterpriseApplication.class, args);
		System.out.println("<---------------------BOOOOOOOOOOOOTED--------------------------->");
	}

}
