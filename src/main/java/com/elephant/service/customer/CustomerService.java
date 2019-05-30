package com.elephant.service.customer;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.elephant.domain.customer.CustomerDomain;
import com.elephant.model.customer.CustomerModel;
import com.elephant.response.Response;

public interface CustomerService {
	
	public Response addCustomer(CustomerModel customerModel,HttpServletRequest request) throws Exception;

	public Response updateCustomer(CustomerModel customerModel, Principal pr) throws Exception;

	public Response deleteCustomer(long customersId) throws Exception;
	
	public CustomerModel getCustomerById(long customerId) throws Exception;
	

	public List<CustomerModel> getCustomers() throws Exception;

	public List<CustomerModel> getcustomerByrollId(int rollId);

	//public CustomerModel getConfirm(String valitate, String email);

	//public String updateCust(CustomerModel customerModel);

	public CustomerModel authenticate(CustomerModel customerModel);

	//public String updateCusto(CustomerModel customerModel);

	public void getConfirm(String valitate, String email);

	public String resetPassword(CustomerModel customerModel);

	public void resetPass(String email, String pass);

	public void createAdmin(CustomerDomain newAdmin);

	public Response addRoleToCustomer(CustomerModel customerModely);

	public void addAdmin(String email, String password,String name);

	public void addRole(String role);

    public boolean checkPassword(CustomerDomain customerDomain,String password);

    public CustomerModel getCustomerByEmail(String email);

	//public void getConfirm(String valitate, String email);

}
  