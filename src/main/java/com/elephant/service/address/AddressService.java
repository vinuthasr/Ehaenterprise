package com.elephant.service.address;

import java.util.List;

import com.elephant.model.address.AddressModel;
import com.elephant.response.Response;

public interface AddressService {

	Response addAddress(AddressModel model,String email) throws Exception;


	Response updateAddressById(AddressModel model)throws Exception;


	List<AddressModel> getAllAddress()throws Exception;


	AddressModel getAddressById(long addressId)throws Exception;


	Response deleteAddressById(long addressId)throws Exception;


	List<AddressModel> getAddressByCustomer(String email);

}
