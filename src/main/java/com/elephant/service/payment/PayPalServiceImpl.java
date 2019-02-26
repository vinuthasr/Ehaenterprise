 package com.elephant.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.elephant.config.PayPalConfig1;

@Service("payPalService")
public class PayPalServiceImpl implements PayPalService1{
	
	@Autowired
	private Environment environment;

	@Override
	public PayPalConfig1 getPayPalConfig() {
		PayPalConfig1 payPalConfig1=new PayPalConfig1();
		payPalConfig1.setAuthToken(environment.getProperty("PayPal.authtoken"));
		payPalConfig1.setBusiness(environment.getProperty("PayPal.business"));
		payPalConfig1.setPosturl(environment.getProperty("PayPal.posturl"));
		payPalConfig1.setReturnurl(environment.getProperty("PayPal.returnurl"));
		
		return payPalConfig1;
		
	}
	

	

	
	
	

}
