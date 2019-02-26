package com.elephant.contoller.customer1;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;

import com.elephant.dao.customer.CustomerRepository;
import com.elephant.service.customer.CustomerService;



@Controller
@RequestMapping("/v1")

public class CustomerController1 {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController1.class);
	
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerRepository cr;
	
	
	
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public  String getConfirm( @RequestParam("email") String email,@RequestParam("validate") String validate,HttpServletRequest request) throws Exception {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		System.out.println(validate);
		
		if(cr.findByEmail(email).isActive()==false) {
		if((cr.findByEmail(email).getValitateCode().equals(validate))) {
		
		
		customerService.getConfirm(validate,email);
		return "Return_registration";
		}else {
			return "email-template3";
		}
			
		}	
		else
		return "email-template3";
	
	}
	@RequestMapping(value="/resettoken",method=RequestMethod.GET)
	public  String getResettoken(Model model, @RequestParam("email") String email,@RequestParam("validate") String validate,HttpServletRequest request) throws Exception {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		System.out.println(validate);
		if(cr.findByEmail(email).getValitateCode().equals(validate) && (cr.findByEmail(email).isActive()==true)) {
			cr.findByEmail(email).setValitateCode(null);
			model.addAttribute("email", email);
			return "resettoken";
		}
	
		return "resettoken";
	
	

}
	@RequestMapping(value="/resettoken1",method=RequestMethod.POST)
	public  String getResettoken1(Model model,@RequestParam("email") String email,@RequestParam("pass") String pass,@RequestParam("newpass") String newpass,HttpServletRequest request) throws Exception {
		logger.info("getConfirm: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		model.addAttribute("email", email);
		
		if(pass.equals(newpass)) {
			customerService.resetPass(email,pass);
			 
			model.addAttribute("success", "success");
		
			
			}
		else if (cr.findByEmail(email).isExpired()) {
			
		    model.addAttribute("error1", "error1");
		    
			
		}
		else {
			model.addAttribute("error", "error");
		
		
		}
	
	return "resettoken";


		
		}
	}
