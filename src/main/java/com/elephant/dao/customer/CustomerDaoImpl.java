package com.elephant.dao.customer;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elephant.constant.StatusCode;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;



@Transactional
@Repository

public class CustomerDaoImpl implements CustomerDao{
private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	BCryptPasswordEncoder pass=new BCryptPasswordEncoder();

	@Override
	public Response addCustomer(CustomerDomain customer) throws Exception {
		Response response = CommonUtils.getResponseObject("Add customer data");
		try {
			
			entityManager.persist(customer);
			response.setStatus(StatusCode.SUCCESS.name());
		} catch (Exception e) {
			logger.error("Exception in addCustomer", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
		
	}


	@Override
	public CustomerDomain getCustomer(long customersId) throws Exception {
		
		try {
			String hql = "FROM CustomerDomain where customersId=?1";
			return (CustomerDomain) entityManager.createQuery(hql).setParameter(1, customersId).getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getCustomerById", e);
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDomain> getCustomers() throws Exception {
		try {
			String hql = "FROM CustomerDomain";
			return (List<CustomerDomain>) entityManager.createQuery(hql).getResultList();
		} catch (Exception e) {
			logger.error("Exception in getcustomers", e);
		}
		return null;
	
	}


	@Override
	public Response deleteCustomer(long customersId) throws Exception {
		Response response = CommonUtils.getResponseObject("Delete Customer data");
		try {
			String hql = "Delete From CustomerDomain Where customersId=?1";
			entityManager.createQuery(hql).setParameter(1, customersId).executeUpdate()	;		
			response.setStatus(StatusCode.SUCCESS.name());
			return response;
		} catch (Exception e) {
			logger.info("Exception in deleteCustomer", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
		
	
	}


	@Override
	public Response updateCustomer(CustomerDomain customer,Principal pr) throws Exception {
		Response response = CommonUtils.getResponseObject("Update customer data");
		try {
			CustomerDomain cust = getCustomer(cr.findByEmail(pr.getName()).getCustomersId());
			if((!customer.getCustomerName().equals(null)) && (!customer.getCustomerName().equals("String"))) {
				cust.setCustomerName(customer.getCustomerName());
			}
			cust.setGender(customer.getGender());
			if((!customer.getEmail().equals(null)) && (!customer.getEmail().equals("String"))) {
				cust.setEmail(customer.getEmail());
			}
			if((!customer.getPassword().equals(null)) && (!customer.getPassword().equals("String"))) {
				cust.setPassword(customer.getPassword());
			}
			cust.setMobileNumber(customer.getMobileNumber());
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("Updated successfully");
			return response;
		} catch (Exception e) {
			logger.error("Exception in updateCustomer", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}



	@Override
	public List<CustomerDomain> getcustomersByrollId(int roleId) {
		
		try {
			String sql = "SELECT c.* FROM customers c, user_roles r where c.customers_id = r.customer_id and r.role_id = ?;";
			return jdbcTemplate.query(sql, new Object[] { roleId }, new BeanPropertyRowMapper<CustomerDomain>(CustomerDomain.class));
		} catch (Exception e) {
			logger.error("Exception in getcustomers", e);
		}
		return null;
	}


	@Override
	public void getConfirm(String valitateCode , String email) {
		// TODO Auto-generated method stub
//		try {
//			String hql = "FROM CustomerDomain where valitateCode=?1";
//			return (CustomerDomain) entityManager.createQuery(hql).setParameter(1, valitateCode).getSingleResult();
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		} catch (Exception e) {
//		logger.error("Exception in getvalidatecode", e);
		
		cr.findByEmail(email).setActive(true);
		cr.findByEmail(email).setValitateCode(null);
		entityManager.flush();
		
		
	}


//	@Override
//	public String updateCust(CustomerDomain customer) {
//		CustomerDomain cust =getConfirm(customer.getValitateCode()) ;
//		cust.setActive(true);
//		//cust.setActiveUser(true);
//	    entityManager.flush();
//	    return null;
//
//	}


	@Override
	public CustomerDomain auteneticate(CustomerDomain customer) {
		
			try {
				//customer.setActiveUser(true);
			    //entityManager.flush();
			    if(pass.matches(customer.getPassword(), cr.findByEmail(customer.getEmail()).getPassword())) {
				String hql = "FROM CustomerDomain where email=?1  and isActive=true";
			    
				return (CustomerDomain) entityManager.createQuery(hql).setParameter(1, customer.getEmail()).getSingleResult();
			}} catch (Exception e) {
				logger.error("Exception in auteneticate", e);
			}
			return null;
		
	}


//	@Override
//	public String updateCusto(CustomerDomain customer) {
//		CustomerDomain cust =getConfirm(customer.getValitateCode()) ;
//		//cust.setActive(true);
//		cust.setActiveUser(true);
//	    entityManager.flush();
//		return null;
//	}}


@Override
public CustomerDomain isUserExist(CustomerDomain customerDomain) {
		try {
			String hql = "FROM CustomerDomain where email=?1 ";
			return (CustomerDomain) entityManager.createQuery(hql).setParameter(1, customerDomain.getEmail()).getSingleResult();
		} catch (Exception e) {
			logger.error("Exception in isUserExist", e);
		}
		return null	;
	
}


@Override
public void resetpassword(String email, String pass) {
	try {
		cr.findByEmail(email).setValitateCode(pass);
		cr.findByEmail(email).setExpiryDate(5);
		
		entityManager.flush();
	}catch (Exception e) {
		logger.error("Exception in resetpassword");
	}
	
}


@Override
public void resetpass(String email, String password) {
	try {

		cr.findByEmail(email).setPassword(password);
		//cr.findByEmail(email).setConfirmPassword(password);
		
		
		//cr.findByEmail(email).setValitateCode(null);
		entityManager.flush();
	}catch (Exception e) {
		logger.error("Exception in resetpassword");
	}
	
}}



	
