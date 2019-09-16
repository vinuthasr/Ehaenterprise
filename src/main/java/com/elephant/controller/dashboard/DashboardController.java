package com.elephant.controller.dashboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.service.customer.CustomerService;
import com.elephant.service.order.OrderService;
import com.elephant.utils.CommonUtils;

@RestController	
@RequestMapping("/v1")
@CrossOrigin(origins= {"http://13.235.82.62","http://localhost:4200"})
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
	@Autowired
	OrderService orderService;
	
	@Autowired
	CustomerService customerService;
	
	//---------- Order Received---------------//
	@RequestMapping(value = "/orderReceived", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String orderReceived( HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("Order Received: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
	    return  CommonUtils.getJson(orderService.getNoOfOrdersReceived());
	
	}
	
	//---------- Total Earnings---------------//
	@RequestMapping(value = "/totalEarnings", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String totalEarnings( HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("Total Earnings: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
	    return  CommonUtils.getJson(orderService.getTotalEarnings());
	
	}
	
	
	//---------- No of New Customers---------------//
	@RequestMapping(value = "/newCustomers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String newCustomers( HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("New Customers: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
	    return  CommonUtils.getJson(customerService.noOfNewCustomers());
	
	}
	
	//---------- No of Active Customers---------------//
	@RequestMapping(value = "/activeCustomers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String activeCustomers(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("Active Customers: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
	    return  CommonUtils.getJson(customerService.noOfActiveCustomers());
	
	}
	
	//---------- Sales Report---------------//
	@RequestMapping(value = "/salesReport", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String salesReport(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("Sales Report: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
	    return  CommonUtils.getJson(orderService.getSalesReport());
	
	}
}
