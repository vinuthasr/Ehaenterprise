package com.elephant.controller.address;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.jwtauthentication.security.services.UserPrinciple;
import com.elephant.model.address.AddressModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.address.AddressService;
import com.elephant.utils.CommonUtils;



@RestController
@RequestMapping(value="/v1/address")
@CrossOrigin(origins= {"https://eha-admin-app.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com"})
public class AddressController {

	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	AddressService addressService;
	
	@PreAuthorize("hasRole('ROLE_USER')" )
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	
	public Response addAddress(@RequestBody AddressModel model, Principal pr,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("addUser: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addUser: Received request: "/* + CommonUtils.getJson(user)*/);
		System.out.println(pr.getName());
		
		
	    return addressService.addAddress(model,pr.getName());
	}
    
	
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
	public Response updateAddressById(@RequestBody AddressModel addressModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateUser: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateUser: Received request: " + CommonUtils.getJson(addressModel));
		return addressService.updateAddressById(addressModel);
	}

	
	
	@RequestMapping(value = "/get/{addressId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getAddressById(@PathVariable("addressId") long addressId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("getUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		AddressModel addressModel = addressService.getAddressById(addressId);
		Response res = CommonUtils.getResponseObject("User Details");
		if (addressModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Users Not Found", "Users Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(addressModel);
		}
		logger.info("getUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Response deleteAddressById(@PathVariable("addressId") long addressId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		return addressService.deleteAddressById(addressId);
	}

	/*@RequestMapping(value = "/userExist/{userName}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String isUserNameExist(@PathVariable("userName") String userName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		boolean isUserNameExist = userService.isUserNameExist(userName);
		Response res = CommonUtils.getResponseObject("User Exist");
		Map<String, Boolean> obj = new HashMap<String, Boolean>();
		obj.put("isUserNameExist", isUserNameExist);
		res.setData(obj);
		if (!isUserNameExist) {
			res.setStatus(StatusCode.ERROR.name());
		}
		logger.info("getUser: Sent response");
		return CommonUtils.getJson(res);
	}
*/
	
	
	@RequestMapping(value = "/getAllAddress", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<AddressModel> getAllAddress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getUsers: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<AddressModel> users = addressService.getAllAddress();
		
		return users;
		/*Response res = CommonUtils.getResponseObject("List of Users");
		if (users == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Users Not Found", "Users Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(users);
		}
		logger.info("getUsers: Sent response");
		return CommonUtils.getJson(res);*/
	}

	
	@RequestMapping(value = "/getAddressByCustomer/{email}", method = RequestMethod.GET, produces = "application/json")
	public String getAddressByCustomer(@RequestParam(value="email") String email) {
		
		Response res = CommonUtils.getResponseObject("Get List of Addresses by Customer");
		List<AddressModel> addressModel = addressService.getAddressByCustomer(email);
		if (addressModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Address Not Found", "Address Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(addressModel);
		}
		logger.info("getUsers: Sent response");
		return CommonUtils.getJson(res);
	}
	/*@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String authenticate(@RequestBody NewAddressDeliveryModel user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("authenticate: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("authenticate :Received request: " + CommonUtils.getJson(user));
		user = newaddressdeliveryservice.authenticate(user);
		Response res = CommonUtils.getResponseObject("authenticate user");
		if (user == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Invalid Username or Password",
					"Invalid Username or Password");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(user);
		}
		logger.info("authenticate: Sent response");
		return CommonUtils.getJson(res);
	}*/
	
	
	/*@RequestMapping(value = "/usersByRole/{roleId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getUsersByRole(@PathVariable("roleId") int roleId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getUsersByOrg: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<NewAddressDeliveryModel> users = newaddressdeliveryservice.getUsersByRole(roleId);
		Response res = CommonUtils.getResponseObject("List of users By Org");
		if (users == null) {
			ErrorObject err = CommonUtils.getErrorResponse("users Not Found", "users Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(users);
		}
		logger.info("getUsers: Sent response");
		return CommonUtils.getJson(res);
	}*/
	
	/*
	@RequestMapping(value = "/user/resetPassword", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody String resetPassword(@RequestBody UserModel userModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("resetPassword: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("resetPassword :Received request: " + CommonUtils.getJson(userModel));
		String status = userService.resetPassword(userModel);
		Response res = CommonUtils.getResponseObject("Reset password");
		if (status.equalsIgnoreCase(StatusCode.ERROR.name())) {
			ErrorObject err = CommonUtils.getErrorResponse("Reset password failed", "Reset password failed");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		}
		logger.info("resetPassword: Sent response");
		return CommonUtils.getJson(res);
	}
	
	
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody String changePassword(@RequestBody UpdatePassword updatePassword, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("changePassword: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("changePassword :Received request: " + CommonUtils.getJson(updatePassword));
		String status = userService.changePassword(updatePassword);
		Response res = CommonUtils.getResponseObject("Update Password");
		res.setStatus(status);
		if (status.equalsIgnoreCase(StatusCode.ERROR.name())) {
			ErrorObject err = CommonUtils.getErrorResponse("Update Password Failed", "Update Password Failed");
			res.setErrors(err);
		}
		logger.info("changePassword: Sent response");
		return CommonUtils.getJson(res);
	}
	
	@RequestMapping(value = "/userStatus", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateUserStatus(@RequestBody UserModel userModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updateUserStatus: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateUserStatus: Received request: " + CommonUtils.getJson(userModel));
		return userService.updateUserStatus(userModel);
	}
	*/

	
	
}
