package com.elephant.service.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephant.constant.StatusCode;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.invoice.InvoiceDao;
import com.elephant.dao.invoice.InvoiceDaoRepository;
import com.elephant.dao.invoicedetails.InvoiceDetailsDaoRepository;
import com.elephant.dao.order.OrderDaoRepository;
import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.invoice.InvoiceDomain;
import com.elephant.domain.invoicedetails.InvoiceDetailsDomain;
import com.elephant.domain.order.OrderDomain;
import com.elephant.domain.orderdetail.OrderDetailDomain;
import com.elephant.mapper.address.AddressMapper;
import com.elephant.mapper.invoice.InvoiceMapper;
import com.elephant.mapper.invoicedeatils.InvoiceDetailsMapper;
import com.elephant.model.address.AddressModel;
import com.elephant.model.invoice.InvoiceModel;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;



@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceDaoRepository invoiceDaoRepository;
	
	@Autowired
	InvoiceDetailsDaoRepository invoiceDetailsDaoRepository;
	
	@Autowired
	CustomerRepository customerRepository ;
	
	@Autowired
	InvoiceDao invoiceDao;
	
	@Autowired
	InvoiceMapper invoiceMapper;
	
	@Autowired
	InvoiceDetailsMapper invoiceDetailsMapper ;
	
	@Autowired
	OrderDaoRepository orderDaoRepository;
	
	@Autowired
	AddressMapper addressMapper;
	
	@Override
	public void generateInvoice(OrderDomain orderDomain) {
		InvoiceModel invoiceModel =new InvoiceModel();
		InvoiceDomain invoiceDomain =new InvoiceDomain();
		BeanUtils.copyProperties(invoiceModel, invoiceDomain);
		
		//CustomerDomain customerDomain=customerRepository.findByEmail(email);
		// Get the Customer and Order Number 
		
		invoiceDomain.setAddressDomain(orderDomain.getAddressDomain());
		invoiceDomain.setCustomerName(orderDomain.getCustomerDomain().getCustomerName());
		invoiceDomain.setOrderNumber(orderDomain.getOrderNumber());
		invoiceDomain.setInvoiceNumber(CommonUtils.generateRandomId());
		invoiceDomain.setInvoiceDate(new Date());
		invoiceDomain.setOrderTotal(orderDomain.getOrderPrice());
		invoiceDomain.setCustomerDomain(orderDomain.getCustomerDomain());
		invoiceDomain.setInvoiceDetailsDomain(new ArrayList<InvoiceDetailsDomain>());
		invoiceDaoRepository.save(invoiceDomain);
		System.out.println("invoice is generated");
		
		//CustomerDomain customerDomain=orderDomain.getCustomerDomain();
		List<OrderDetailDomain> listOrderDetailDomain=orderDaoRepository.findByOrderId(orderDomain.getOrderId()).getOrderDetailDomain();
		for(OrderDetailDomain orderDetailDomain: listOrderDetailDomain ) {
			InvoiceDetailsDomain invoiceDetailsDomain=new InvoiceDetailsDomain ();
			
			invoiceDetailsDomain.setProductSku(orderDetailDomain.getProductSku());
			invoiceDetailsDomain.setProductName(orderDetailDomain.getProductName());
			invoiceDetailsDomain.setProductQuantity(orderDetailDomain.getProductQuantity());
			invoiceDetailsDomain.setProductAmount(orderDetailDomain.getProductAmount());
			invoiceDetailsDomain.setInvoiceDomain(invoiceDomain);
			invoiceDetailsDaoRepository.save(invoiceDetailsDomain);
			invoiceDomain.getInvoiceDetailsDomain().add(invoiceDetailsDomain);
		}
		System.out.println("orderdetails generated");
		
	}

	@Override
	public List<InvoiceModel> getInvoiceByCustomer(String email) {
		try {
		
			CustomerDomain customerDomain=customerRepository.findByEmail(email);
			List<InvoiceDomain> invoiceDomainList=customerDomain.getInvoiceDomain();
	
			List<InvoiceModel> invoiceModelList=new ArrayList<InvoiceModel>();
			for(InvoiceDomain invoiceDomain:invoiceDomainList) {
				InvoiceModel invoiceModel=invoiceMapper.entity(invoiceDomain);
				invoiceModel.setInvoiceDetailsModel(invoiceDetailsMapper.entityList(invoiceDomain.getInvoiceDetailsDomain()));
				invoiceModel.setAddressModel(addressMapper.entity(invoiceDomain.getAddressDomain()));
				invoiceModelList.add(invoiceModel);
			}
			return invoiceModelList;
			
			//return invoiceMapper.entityList(invoiceDomain);
			}catch(Exception ex) {
				System.out.println("Excpetion in getInvoiceByCustomer"+ex);
			}
			return null;
	}

	@Override
	public List<InvoiceModel> getAllInvoices() {
		try {
			List<InvoiceDomain> invoiceDomainList=invoiceDao.getAllInvoices();
			List<InvoiceModel> invoiceModelList=new ArrayList<InvoiceModel>();
			for(InvoiceDomain invoiceDomain:invoiceDomainList) {
				InvoiceModel invoiceModel=invoiceMapper.entity(invoiceDomain);
				invoiceModel.setInvoiceDetailsModel(invoiceDetailsMapper.entityList(invoiceDomain.getInvoiceDetailsDomain()));
				invoiceModel.setAddressModel(addressMapper.entity(invoiceDomain.getAddressDomain()));
				invoiceModelList.add(invoiceModel);
			}
			return invoiceModelList;
			
			//return invoiceMapper.entityList(invoiceDomain);
			}catch(Exception ex) {
				System.out.println("Excpetion in getAllInvoices"+ex);
			}
			return null;
	}

	@Override
	public List<InvoiceModel> getInvoiceByDate(Date invoiceDate) {
		try {
			List<InvoiceDomain> invoiceDomain=invoiceDao.getInvoiceByDate(invoiceDate);
			return invoiceMapper.entityList(invoiceDomain);
			}catch(Exception ex) {
				System.out.println("Excpetion in getInvoiceByDate"+ex);
			}
			return null;
	}

	@Override
	public List<InvoiceModel> getInvoiceBetweenDates(Date fromDate, Date toDate) {
		try {
			List<InvoiceDomain> invoiceDomain=invoiceDao.getInvoiceBetweenDates(fromDate,toDate);
			return invoiceMapper.entityList(invoiceDomain);
			}catch(Exception ex) {
				System.out.println("Excpetion in getInvoiceBetweenDates"+ex);
			}
			return null;
		
	}

	@Override
	public Response deleteAllInvoices() {
		Response res=CommonUtils.getResponseObject("Delete All Invoices");
		try {
		 invoiceDaoRepository.deleteAll();
		 res.setStatus(StatusCode.SUCCESS.name());
		 return res;
	}catch(Exception ex) {
		res.setStatus(StatusCode.ERROR.name());
		System.out.println("Exception in deleteAllOrders"+ex);
		return res;
	}
		
	}
	
public AddressModel getAddressByInvoiceId(long invoiceId) {
		
	try {
		InvoiceDomain invoiceDomain=invoiceDaoRepository.findByInvoiceId(invoiceId);
		AddressDomain addressDomain=invoiceDomain.getAddressDomain();
		AddressModel addressModel=new AddressModel();
		BeanUtils.copyProperties(addressDomain, addressModel);
		return addressModel;
	}catch(Exception ex) {
		System.out.println("Exception in getAddressByInvoiceId "+ex);
	}
		
	return null;
	}
		
	
}
