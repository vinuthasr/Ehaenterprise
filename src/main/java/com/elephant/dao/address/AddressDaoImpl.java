package com.elephant.dao.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.elephant.constant.StatusCode;
import com.elephant.domain.address.AddressDomain;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;


@Repository
@Transactional
public class AddressDaoImpl implements AddressDao{

	
	private static final Logger logger = LoggerFactory.getLogger(AddressDaoImpl.class);

	@PersistenceContext
	EntityManager entityManager;
	
	public Response addAddress(AddressDomain addressDomain)throws Exception {
	Response response = CommonUtils.getResponseObject("Add Address");
	try {
		entityManager.persist(addressDomain);
		response.setStatus(StatusCode.SUCCESS.name());
	} catch (Exception e) {
		logger.error("Exception in Add Address", e);
		response.setStatus(StatusCode.ERROR.name());
		response.setErrors(e.getMessage());
	}
	return response;
	}
	
	public Response deleteAddressById(long addressId) {
		Response response = CommonUtils.getResponseObject("Delete Address data");
		try {
			String hql = "Delete From AddressDomain Where addressId=?1";
			entityManager.createQuery(hql).setParameter(1, addressId).executeUpdate();		
			response.setStatus(StatusCode.SUCCESS.name());
			return response;
		} catch (Exception e) {
			logger.info("Exception in delete Address", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}
	
	public Response updateAddressById(AddressDomain domain) throws Exception {
		Response response = CommonUtils.getResponseObject("Update Address data");
		try {
			AddressDomain domain1=getAddressById(domain.getAddressId());
			//AddressDomain domain1 = new AddressDomain();
			domain1.setFullname(domain.getFullname());
			domain1.setAddressline1(domain.getAddressline1());
			domain1.setAddressline2(domain.getAddressline2());
			domain1.setAddressline3(domain.getAddressline3());
			domain1.setCity(domain.getCity());
			domain1.setCountry(domain.getCountry());
			domain1.setPincode(domain.getPincode());
			domain1.setState(domain.getState());
			domain1.setTown(domain.getTown());
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
		} catch (Exception e) {
			logger.error("Exception in updateAddress", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return  response;
	}
	
	@Override
	public AddressDomain getAddressById(long addressId) throws Exception {
		String hql="FROM AddressDomain WHERE addressId=?1";
		return (AddressDomain) entityManager.createQuery(hql).setParameter(1, addressId).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AddressDomain> getAllAddress() throws Exception {
		try {
			String hql = "FROM AddressDomain";
			return (List<AddressDomain>) entityManager.createQuery(hql).getResultList();
		} catch (Exception e) {
			logger.error("Exception in getAllAddress", e);
		}
		return null;
	}

	

	
}
