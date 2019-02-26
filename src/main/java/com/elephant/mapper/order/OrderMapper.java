package com.elephant.mapper.order;

import org.springframework.stereotype.Component;

import com.elephant.domain.order.OrderDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.order.OrderModel;

@Component
public class OrderMapper extends AbstractModelMapper<OrderModel, OrderDomain> {

	@Override
	public Class<OrderModel> entityType() {
		return OrderModel.class;
	}

	@Override
	public Class<OrderDomain> modelType() {
		return OrderDomain.class;
	}
	
}
