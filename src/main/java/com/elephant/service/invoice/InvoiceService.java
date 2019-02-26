package com.elephant.service.invoice;

import java.util.Date;
import java.util.List;

import com.elephant.domain.order.OrderDomain;
import com.elephant.model.address.AddressModel;
import com.elephant.model.invoice.InvoiceModel;
import com.elephant.response.Response;

public interface InvoiceService {

	public void generateInvoice(OrderDomain orderDomain);

	public List<InvoiceModel> getInvoiceByCustomer(String email);

	public List<InvoiceModel> getAllInvoices();

	public List<InvoiceModel> getInvoiceByDate(Date invoiceDate);

	public List<InvoiceModel> getInvoiceBetweenDates(Date fromDate, Date toDate);

	public Response deleteAllInvoices();

	public AddressModel getAddressByInvoiceId(long invoiceId);

}
