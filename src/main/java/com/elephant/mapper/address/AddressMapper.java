package com.elephant.mapper.address;

import org.springframework.stereotype.Component;

import com.elephant.domain.address.AddressDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.address.AddressModel;

@Component
public class AddressMapper extends AbstractModelMapper<AddressModel, AddressDomain> {

	
	@Override
	public Class<AddressModel> entityType() {
		return AddressModel.class;
	}

	@Override
	public Class<AddressDomain> modelType() {
		return AddressDomain.class;
	}
}
