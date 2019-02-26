package com.elephant.mapper.invoice;

import org.springframework.stereotype.Component;

import com.elephant.domain.invoice.InvoiceDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.invoice.InvoiceModel;

@Component
public class InvoiceMapper extends AbstractModelMapper<InvoiceModel, InvoiceDomain> {

	@Override
	public Class<InvoiceModel> entityType() {
		return InvoiceModel.class;
	}

	@Override
	public Class<InvoiceDomain> modelType() {
		return InvoiceDomain.class;
	}
	
	
	
	
}
