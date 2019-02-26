package com.elephant.dao.address;

import java.util.List;

import com.elephant.domain.address.AddressDomain;
import com.elephant.response.Response;

public interface AddressDao {

	Response addAddress(AddressDomain addressDomain)throws Exception;

	Response deleteAddressById(long addressId) throws Exception;

	Response updateAddressById(AddressDomain addressDomain) throws Exception;

	List<AddressDomain> getAllAddress() throws Exception;

	AddressDomain getAddressById(long addressId) throws Exception;

}
