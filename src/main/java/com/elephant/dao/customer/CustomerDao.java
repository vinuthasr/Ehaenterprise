package com.elephant.dao.customer;

import java.security.Principal;
import java.util.List;

import com.elephant.domain.customer.CustomerDomain;
import com.elephant.response.Response;

public interface CustomerDao {
	
	public Response addCustomer(CustomerDomain customer) throws Exception;

	public Response updateCustomer(CustomerDomain customer, Principal pr) throws Exception;

	public CustomerDomain getCustomer(long CustomersId) throws Exception;
	
	public Response deleteCustomer(long customersId) throws Exception;
//
	public List<CustomerDomain> getCustomers() throws Exception;
//
	public List<CustomerDomain> getcustomersByrollId(int rollId);
//
//	public Response updateCustomerStatus(CustomerDomain customer);

	public void getConfirm(String valitateCode, String email);

	//public String updateCust(CustomerDomain customer);

	public CustomerDomain auteneticate(CustomerDomain customer);

	public CustomerDomain isUserExist(String email);

	public void resetpassword(String email, String password) ;

	public void resetpass(String email, String password);

	public Integer getNoOfNewCustomers();
	
	public Integer getNoOfActiveCustomers();
}
