package com.elephant.jwtauthentication.security.services;

import com.elephant.dao.customer.CustomerRepository;
import com.elephant.domain.customer.CustomerDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CustomerRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
    	if(userRepository.findByEmail(email).isActive()==false) {
        CustomerDomain user = userRepository.findByEmail(email);
    	        	
        return UserPrinciple.build(user);
    }
    	else
    		return UserPrinciple.build(null);
    }
}