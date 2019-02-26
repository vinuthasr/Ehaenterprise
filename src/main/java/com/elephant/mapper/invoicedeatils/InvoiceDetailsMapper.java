package com.elephant.mapper.invoicedeatils;

import org.springframework.stereotype.Component;

import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.invoicedetails.InvoiceDetailsDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.cartitem.CartItemModel;
import com.elephant.model.invoicedetails.InvoiceDetailsModel;

@Component
public class InvoiceDetailsMapper  extends AbstractModelMapper<InvoiceDetailsModel,InvoiceDetailsDomain>{

	@Override
	public Class<InvoiceDetailsModel> entityType() {
		return InvoiceDetailsModel.class;
	}

	@Override
	public Class<InvoiceDetailsDomain> modelType() {
		return InvoiceDetailsDomain.class;
	}
	
}
