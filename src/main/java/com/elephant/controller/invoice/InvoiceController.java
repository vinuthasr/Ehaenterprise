/*package com.elephant.controller.invoice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.model.address.AddressModel;
import com.elephant.model.invoice.InvoiceModel;
import com.elephant.model.order.OrderModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.invoice.InvoiceService;
import com.elephant.utils.CommonUtils;


@RestController
@RequestMapping(value="/invoice")
public class InvoiceController {

	
	@Autowired
	InvoiceService invoiceService;
	
	@RequestMapping(value="/generate/{email}", method=RequestMethod.GET, produces="application/json")
	public String getInvoiceByCustomer(@RequestParam(value="email") String email) {

		Response res = CommonUtils.getResponseObject(" Get customer  invoice");
    	List<InvoiceModel> invoiceModel=invoiceService.getInvoiceByCustomer(email);
		if (invoiceModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Invoice Is null", "Invoice Is null");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(invoiceModel);
		}
		
		return CommonUtils.getJson(res);
		
	}
	
	@RequestMapping(value="/generate/", method=RequestMethod.GET, produces="application/json")
	public String getAllInvoices() {
		
		Response res = CommonUtils.getResponseObject("total orders per day");
    	List<InvoiceModel> invoiceModel=invoiceService.getAllInvoices();
		if (invoiceModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Invoices are null", "Invoice are null");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(invoiceModel);
		}
		
		return CommonUtils.getJson(res);
		
	}
	@RequestMapping(value="/getInvoiceByDate/{invoiceDateString}", method=RequestMethod.GET, produces="application/json")
	public String getInvoiceByDate(@RequestParam(value="DateString")String invoiceDateString) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date invoiceDate = format.parse(invoiceDateString);
    		
		
	Response res = CommonUtils.getResponseObject("Get Invoices By date");
	List<InvoiceModel> invoiceModel=invoiceService.getInvoiceByDate(invoiceDate);
	if (invoiceModel == null) {
		ErrorObject err = CommonUtils.getErrorResponse("Invoices are null by this date", "Invoice are null by this date");
		res.setErrors(err);
		res.setStatus(StatusCode.ERROR.name());
	} else {
		res.setData(invoiceModel);
	}
	return CommonUtils.getJson(res);
	
	}
	
	@RequestMapping(value="/getInvoiceByDate/{fromDateString}/{toDateString}", method=RequestMethod.GET, produces="application/json")
	public String getInvoiceBetweenDates(@RequestParam(value="fromDateString")String fromDateString, @RequestParam(value="toDateString")String toDateString) throws ParseException {
		
		--------------String To Date---------------
    	  Convert from String to Date
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date fromDate = format.parse(fromDateString);
    	Date toDate=format.parse(toDateString);
    	------------------------------------------
    	
    	Response res = CommonUtils.getResponseObject("totalOrdersFromDateToParticularDate");
    	List<InvoiceModel> invoiceModel=invoiceService.getInvoiceBetweenDates(fromDate,toDate);
    	
		if (invoiceModel == null) {
		
			ErrorObject err = CommonUtils.getErrorResponse("Invoices Not Found", "Invoices Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} 
			else {
			
			res.setData(invoiceModel);
		}
		
    	return CommonUtils.getJson(res);
	}

	@RequestMapping(value="/deleteAllInvoices", method=RequestMethod.DELETE, produces="application/json")
	public Response deleteAllInvoices() {
		return invoiceService.deleteAllInvoices();
	}

	@RequestMapping(value="/getAddressByInvoiceId/{invoiceId}", method=RequestMethod.GET, produces="application/json")
	public String getAddressByInvoiceId(@PathVariable(value="invoiceId") long invoiceId){
		Response res = CommonUtils.getResponseObject("totalOrdersFromDateToParticularDate");
    	AddressModel addressModel=invoiceService.getAddressByInvoiceId(invoiceId);
    	
		if (addressModel == null) {
		
			ErrorObject err = CommonUtils.getErrorResponse("Address Not Found", "Address Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} 
			else {
			
			res.setData(addressModel);
		}
		
    	return CommonUtils.getJson(res);
	}
	
	
}
*/