package com.elephant.mapper.cartitem;


import org.springframework.stereotype.Component;

import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.cartitem.CartItemModel;

@Component
public class CartItemMapper extends AbstractModelMapper<CartItemModel, CartItemDomain> {

	@Override
	public Class<CartItemModel> entityType() {
		return CartItemModel.class;
	}

	@Override
	public Class<CartItemDomain> modelType() {
		return CartItemDomain.class;
	}
}
