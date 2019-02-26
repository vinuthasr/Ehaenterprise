package com.elephant.mapper.orderdetail;


import org.springframework.stereotype.Component;

import com.elephant.domain.orderdetail.OrderDetailDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.orderdetail.OrderDetailModel;

@Component
public class OrderDetailMapper extends AbstractModelMapper<OrderDetailModel, OrderDetailDomain>{

	@Override
	public Class<OrderDetailModel> entityType() {
		// TODO Auto-generated method stub
		return OrderDetailModel.class;
	}

	@Override
	public Class<OrderDetailDomain> modelType() {
		// TODO Auto-generated method stub
		return OrderDetailDomain.class;
	}

}
