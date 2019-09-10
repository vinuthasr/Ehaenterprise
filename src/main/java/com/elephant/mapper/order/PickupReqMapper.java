package com.elephant.mapper.order;

import org.springframework.stereotype.Component;

import com.elephant.domain.courier.PickupRequestDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.courier.PickupReqModel;

@Component
public class PickupReqMapper extends AbstractModelMapper<PickupReqModel, PickupRequestDomain> {

	@Override
	public Class<PickupReqModel> entityType() {
		// TODO Auto-generated method stub
		return PickupReqModel.class;
	}

	@Override
	public Class<PickupRequestDomain> modelType() {
		// TODO Auto-generated method stub
		return PickupRequestDomain.class;
	}

}
