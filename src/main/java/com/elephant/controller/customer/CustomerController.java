 package com.elephant.controller.customer;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.dao.customer.CustomerDao;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.jwtauthentication.message.response.JwtResponse;
import com.elephant.jwtauthentication.security.jwt.JwtProvider;
import com.elephant.model.customer.CustomerModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.customer.CustomerService;
import com.elephant.utils.CommonUtils;



@RestController	
@RequestMapping("/v1")
@CrossOrigin(origins= {Constants.ADMIN_URL,Constants.CUSTOMER_URL,Constants.LOCALHOST_URL})
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
    @Autowired
    CustomerDao customerDao;
    

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    CustomerRepository cr;
	
	
	//---------- Add Customer---------------//
	
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
	public Response addRole(@RequestBody CustomerModel customerModel,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("addRoleToCustomer: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addRoleToCustomer: Received Request: " + CommonUtils.getJson(customerModel));
		
		return customerService.addRoleToCustomer(customerModel);
	}
	
	
	//------------------- get all customerss--------------//
	
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
	
	//------------------ get customer by Id-----------------//
	
	@RequestMapping(value = "/get/{Id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCustomerById(@PathVariable("Id") long customersId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getCustomer: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		CustomerModel customerModel = customerService.getCustomerById(customersId);
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
	public @ResponseBody Response deleteCustomer(@PathVariable("customerId") long customerId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteCustomer: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		return customerService.deleteCustomer(customerId);
	}
	
	//====================== update customer ===================//
	
	//@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	public Response updateUser(@RequestBody CustomerModel customerModel, Principal pr, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateCustomer: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateCustomer: Received request: " + CommonUtils.getJson(customerModel));
		return customerService.updateCustomer(customerModel,pr);
	}
  

	
	
	@RequestMapping(value = "/getroll/{rollId}", method = RequestMethod.GET, produces = "application/json")
	
	public @ResponseBody String getCustomersByRollId(@PathVariable("rollId") int rollId, HttpServletRequest request,
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
	
	@RequestMapping(value = "/customer/resetPassword/{email}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody String resetPassword(@RequestParam(value="email")String email, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("resetPassword: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("resetPassword :Received request: " + CommonUtils.getJson(email));
		String status = customerService.resetPassword(email);
		Response res = CommonUtils.getResponseObject("Reset password");
		if (status.equalsIgnoreCase(StatusCode.ERROR.name())) {
			ErrorObject err = CommonUtils.getErrorResponse("Reset password failed", "Reset password failed");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setStatus(StatusCode.SUCCESS.name());
			res.setMessage1("Password reset link has been sent to your mail Id. Link will expire within one hour");
		}
		
		logger.info("resetPassword: Sent response");
		return CommonUtils.getJson(res);
	}
	
	 @PostMapping(value="/signin",produces="application/json")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody CustomerModel loginRequest) throws Exception {
		
		 HashMap<String,Object> loginMap = new HashMap<String, Object>();
		   if(cr != null) {
			   CustomerDomain customerDomain = cr.findByEmail(loginRequest.getEmail());
			   boolean isValidPwd = customerService.checkPassword(customerDomain,loginRequest.getPassword());
			   if(customerDomain != null && isValidPwd) {
				   if(customerDomain.isActive()==true) {
				        Authentication authentication = authenticationManager.authenticate(
				                new UsernamePasswordAuthenticationToken(
				                        loginRequest.getEmail(),
				                        loginRequest.getPassword()
				                )
				        );
				        
				        
				        SecurityContextHolder.getContext().setAuthentication(authentication);
				        String jwt = jwtProvider.generateJwtToken(authentication);
				        loginMap.put("jwt", new JwtResponse(jwt).getAccessToken());
				        loginMap.put("userName", customerDomain.getCustomerName());
				        loginMap.put("email", customerDomain.getEmail());
				        loginMap.put("role", customerDomain.getRoles().get(0).getName());
			        }
		            else 
		            {
		            	loginMap.put("status", StatusCode.ERROR.name());
		            	loginMap.put("message", "Email is not verified");
		            	
		            }
			        
			   } else {
				    loginMap.put("status", StatusCode.ERROR.name());
	            	loginMap.put("message", "Username / Password is incorrect");
			   }
			   
		   } else {
			    loginMap.put("status", StatusCode.ERROR.name());
           		loginMap.put("message", "Username / Password is incorrect");
		   }
		   
		   return ResponseEntity.ok().body(loginMap);
            
	    }


	 @RequestMapping(value = "/getByEmail/{email}", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody String getCustomerByEmail(@PathVariable("email") String email, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			logger.info("getCustomerByEmail: Received request: " + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
			Response res = CommonUtils.getResponseObject("Customer Details");
			if(null != email) {
				CustomerModel customerModel = customerService.getCustomerByEmail(email);
				
				if (customerModel == null) {
					ErrorObject err = CommonUtils.getErrorResponse("Customer Not Found", "Customers Not Found");
					res.setErrors(err);
					res.setStatus(StatusCode.ERROR.name());
				} else {
					res.setData(customerModel);
				}
			}
			
			logger.info("getCustomerByEmail: Sent response");
			return CommonUtils.getJson(res);
		}
	 
	//Export to excel for customer list
	@RequestMapping(value = "/excel/Customer.xlsx", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> customerExportToExcel() {
		ByteArrayInputStream   in = null;
		try {
			in = customerService.customerExportToExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
	        .header("Content-disposition", "attachment; filename=Customer.xlsx") // add headers if any
	        .body(new InputStreamResource(in));
	}
	
}





