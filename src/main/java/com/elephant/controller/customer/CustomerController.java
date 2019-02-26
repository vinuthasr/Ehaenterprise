 package com.elephant.controller.customer;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.dao.customer.CustomerDao;
import com.elephant.jwtauthentication.message.response.JwtResponse;
import com.elephant.jwtauthentication.security.jwt.JwtProvider;
import com.elephant.model.customer.CustomerModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.customer.CustomerService;
import com.elephant.utils.CommonUtils;


//@Controller
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins="https://sheltered-fortress-53647.herokuapp.com",allowedHeaders="*")
public class CustomerController {
	
	//@PersistenceContext
	//private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
    @Autowired
    CustomerDao customerDao;
    

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtProvider jwtProvider;
	
	
	//---------- post---------------//
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public Response addCustomer(@RequestBody CustomerModel customerModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("addCustomers: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addCustomers: Received Request: " + CommonUtils.getJson(customerModel));
	return customerService.addCustomer(customerModel,request);
	
	
	}
	
	
	

	//---------- role_post---------------//
	
	@RequestMapping(value = "/roleadd", method = RequestMethod.POST, produces = "application/json")
	public Response addRole(@RequestBody CustomerModel customerModel,Model model1, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("addCustomers: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addCustomers: Received Request: " + CommonUtils.getJson(customerModel));
	return customerService.addCustomer1(customerModel,model1);
	}
	//------------------- get all--------------//
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCustomers(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getCustomers List: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<CustomerModel> customerModel = customerService.getCustomers();
		Response res = CommonUtils.getResponseObject("List of Customers");
		if (customerModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Customers Not Found", "Customers Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(customerModel);
		}
		logger.info("getCustomers: Sent response");
		return CommonUtils.getJson(res);
	}
	
	//------------------ get by Id-----------------//
	
	@RequestMapping(value = "/get/{Id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCustomers(@PathVariable("Id") long customersId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getCustomer: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		CustomerModel customerModel = customerService.getCustomer(customersId);
		Response res = CommonUtils.getResponseObject("Customer Details");
		if (customerModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Customer Not Found", "Customers Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(customerModel);
		}
		logger.info("getCustomer: Sent response");
		return CommonUtils.getJson(res);
	}
	
	//------------------ delete --------------------//
	@RequestMapping(value = "/delete/{customerId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Response deleteCustomer(@PathVariable("customerId") long customersId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteCustomer: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		return customerService.deleteCustomer(customersId);
	}
	
	//====================== update customer ===================//
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	public Response updateUser(@RequestBody CustomerModel customerModel, Principal pr, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateCustomer: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateCustomer: Received request: " + CommonUtils.getJson(customerModel));
		return customerService.updateCustomer(customerModel,pr);
	}
  

	
	
	@RequestMapping(value = "/getroll/{rollId}", method = RequestMethod.GET, produces = "application/json")
	
	public @ResponseBody String getCustomersByRollId(@PathVariable("rollId") long rollId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		logger.info("getCustomerbyrollid List: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		
		List<CustomerModel> customerModel = customerService.getcustomerByrollId(rollId);
		
		
		Response res = CommonUtils.getResponseObject("List of Customersbyrollid");
		
		if (customerModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Customer Not Found", "Customers Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(customerModel);
		}
		
		logger.info("getCustomer: Sent response");
		return CommonUtils.getJson(res);
	}
	
	
	

		

	
	
	
	
	@RequestMapping(value = "/login2", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String authenticate(@RequestBody CustomerModel customerModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("authenticate: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("authenticate :Received request: " + CommonUtils.getJson(customerModel));
		customerModel = customerService.authenticate(customerModel);
		Response res = CommonUtils.getResponseObject("authenticate user");
		if (customerModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Invalid Username or Password",
					"Invalid Username or Password");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			
			 
			 res.setData(customerModel);
		}
		logger.info("authenticate: Sent response");
		return CommonUtils.getJson(res);
	}
	
	@RequestMapping(value = "/customer/resetPassword", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody String resetPassword(@RequestBody CustomerModel customerModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("resetPassword: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("resetPassword :Received request: " + CommonUtils.getJson(customerModel));
		String status = customerService.resetPassword(customerModel);
		Response res = CommonUtils.getResponseObject("Reset password");
		if (status.equalsIgnoreCase(StatusCode.ERROR.name())) {
			ErrorObject err = CommonUtils.getErrorResponse("Reset password failed", "Reset password failed");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}
		logger.info("resetPassword: Sent response");
		return CommonUtils.getJson(res);
	}
	
	 @PostMapping(value="/signin",produces="application/json")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody CustomerModel loginRequest) {

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String jwt = jwtProvider.generateJwtToken(authentication);
	        String userName=jwtProvider.getUserNameFromJwtToken(jwt);
	       
	        return ResponseEntity.ok(new JwtResponse(jwt));
	    }


	
}





