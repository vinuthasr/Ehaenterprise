package com.elephant.dao.invoice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.elephant.dao.customer.CustomerRepository;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.invoice.InvoiceDomain;

@Repository
public class InvoiceDaoImpl  implements InvoiceDao{

	
	@Autowired
	InvoiceDaoRepository invoiceDaoRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public List<InvoiceDomain> getAllInvoices() {
		
		return invoiceDaoRepository.findAll();
	}

	@Override
	public List<InvoiceDomain> getInvoiceByDate(Date invoiceDate) {
		
		return invoiceDaoRepository.findAllByInvoiceDate(invoiceDate) ;
	}

	@Override
	public List<InvoiceDomain> getInvoiceBetweenDates(Date fromDate, Date toDate) {
	
		return invoiceDaoRepository.findAllByInvoiceDateBetween(fromDate, toDate);
	}

	/*@Override
	public List<InvoiceDomain> getInvoiceByCustomer(String email) {
		
		
		return invoiceDaoRepository.findAllByEmail(email);
	}
*/
	/*@Override
	public List<InvoiceDomain> getInvoiceByCustomer(String email) {
		CustomerDomain customerDomain=customerRepository.findByEmail(email);
		//List<InvoiceDomain> inoiceDomain=customerDomain.get
		//return 
	}*/

}
