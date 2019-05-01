package com.elephant.controller.order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.model.address.AddressModel;
import com.elephant.model.order.OrderModel;
import com.elephant.model.orderdetail.OrderDetailModel;
import com.elephant.model.payment.PaymentModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.order.OrderService;
import com.elephant.utils.CommonUtils;

@RestController
@RequestMapping(value="/v1/Orders")
@CrossOrigin(origins= {"https://eha-admin-app.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com"})
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	
	@RequestMapping(value = "/ordercreate/{email}/{addressId}/{paymentMode}",method=RequestMethod.POST, produces="application/json")
	@CrossOrigin(origins="https://sheltered-fortress-53647.herokuapp.com",allowedHeaders="*")
    public Response createOrder(@RequestParam(value="email")String email, @PathVariable(value="addressId")long addressId ,@RequestParam(value="paymentMode") String paymentMode) throws IOException{
		
		PaymentModel paymentModel=new PaymentModel();
		paymentModel.setEmail(email);
		paymentModel.setAddressId(addressId);
		paymentModel.setPaymentMode(paymentMode);
       return orderService.createOrder(paymentModel);
	}
        
    @RequestMapping(value = "/getAllOrders",method=RequestMethod.GET, produces="application/json")
    public String getAllOrders(){
    	Response res = CommonUtils.getResponseObject("Get All Customer Orders");
    	List<OrderModel> orderModel= orderService.getAllOrders();
		if (orderModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Orders Not Found", "Orders Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orderModel);
		}
		return CommonUtils.getJson(res);
        
    }

    @RequestMapping(value = "/getOrderByCustomer/{email}",method=RequestMethod.GET, produces="application/json")
    public String getAllCustomerOrders(@RequestParam(value="email") String email){
    	
    	Response res = CommonUtils.getResponseObject("Get All Customer Orders");
    	List<OrderModel> orderModel=orderService.getAllCustomerOrders(email);
		if (orderModel.isEmpty() ) {
			ErrorObject err = CommonUtils.getErrorResponse("Orders Not Found", "Orders Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orderModel);
		}
		
		return CommonUtils.getJson(res);
    	
    }
	

    @RequestMapping(value = "/totalOrdersAmountPerDay/{orderDateDtring}",method=RequestMethod.GET, produces="application/json")
	public double totalOrdersAmountPerDay(@RequestParam(value="orderDateString")String orderDateString) throws ParseException{
    	/*--------------String To Date---------------*/
    	/*  Convert from String to Date*/
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date orderDate = format.parse(orderDateString);
    	
		return orderService.totalOrdersAmountPerDay(orderDate);
	} 
	
    @RequestMapping(value = "/getOrderFromDateToParticularDate/{fromDateString}/{toDateString}",method=RequestMethod.GET, produces="application/json")
	public String totalOrdersFromDateToParticularDate(@RequestParam(value="fromDateString")String fromDateString, @RequestParam(value="toDateString")String toDateString) throws ParseException{
		
    	/*--------------String To Date---------------*/
    	/*  Convert from String to Date*/
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date fromDate = format.parse(fromDateString);
    	Date toDate=format.parse(toDateString);
    	/*------------------------------------------*/
    	
    	
    	Response res = CommonUtils.getResponseObject("totalOrdersFromDateToParticularDate");
    	List<OrderModel> orderModel=orderService.totalOrdersFromDateToParticularDate(fromDate,toDate);
    	
		if (orderModel.isEmpty()) {
		
			ErrorObject err = CommonUtils.getErrorResponse("Orders Not Found", "Orders Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} 
			else {
			
			res.setData(orderModel);
		}
		
		return CommonUtils.getJson(res);
    	
	} 
    
    
    @RequestMapping(value = "/cancelOrder/{orderId}",method=RequestMethod.POST, produces="application/json")
    public Response cancelOrder(@RequestParam(value="orderId")long orderId){
		return orderService.cancelOrder(orderId);
	}
	
    @RequestMapping(value = "/getOrdersByDate/{orderDateString}",method=RequestMethod.GET, produces="application/json")
	public String getOrdersByDateController(@RequestParam(value="orderDate") String orderDateString) throws ParseException {
    	Response res = CommonUtils.getResponseObject("total orders per day");
    	
    	/*  Convert from String to Date*/
    	//String string = "January 2, 2010";
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date orderDate = format.parse(orderDateString);
    	
    	List<OrderModel> orderModel=orderService.getOrdersByDate(orderDate);
		if (orderModel.isEmpty()) {
			ErrorObject err = CommonUtils.getErrorResponse("Orders are Null by this Date", "Orders are Null by this Date");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orderModel);
		}
		
		return CommonUtils.getJson(res);
	}
    
    @RequestMapping(value = "/deleteAllOrders",method=RequestMethod.DELETE, produces="application/json")
    public Response deleteAllOrders() {
    	return orderService.deleteAllOrders();
    }

    
    @RequestMapping(value = "/getOrderDetailsByOrderId/{orderId}",method=RequestMethod.GET, produces="application/json")
    public String getOrderDetailsByOrderId(@RequestParam(value="orderId") long orderId) throws ParseException {
    	
    	Response res = CommonUtils.getResponseObject("getOrderDetailsByCustomer");
    	List<OrderDetailModel>  orderDetailModel=orderService.getOrderDetailsByOrderId(orderId);
		if (orderDetailModel.isEmpty()) {
			ErrorObject err = CommonUtils.getErrorResponse("Orders are Null by this Date", "Orders are Null by this Date");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orderDetailModel);
		}
		
		return CommonUtils.getJson(res);
    	
	}
    

    @RequestMapping(value = "/getAddressByOrderId/{orderId}",method=RequestMethod.GET, produces="application/json")
    public String getAddressByOrderId(long orderId) {
    	
    	Response res=CommonUtils.getResponseObject("Get Address by OrderId");
    	AddressModel addressModel=orderService.getAddressByOrderId(orderId);
    	if(addressModel==null) {
    		ErrorObject err=CommonUtils.getErrorResponse("Address is null", "Address is null");
    		res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(addressModel);
		}
		
		return CommonUtils.getJson(res);
    }
    
    @RequestMapping(value = "/updateOrderStatus",method=RequestMethod.PUT, produces="application/json")
    private Response updateOrderStatus(@RequestBody OrderModel orderModel) {
		return orderService.updateOrderStatus(orderModel);
    	
    }

    @RequestMapping(value = "/getOrderByOrderId/{orderId}",method=RequestMethod.GET, produces="application/json")
    private String getOrderByOrderId(@PathVariable(value="orderId") long orderId) {
    	Response response=CommonUtils.getResponseObject("Get Order by OrderId");
    	OrderModel orderModel=orderService.getOrderByOrderId(orderId);
    	if(orderModel==null) {
    		ErrorObject err=CommonUtils.getErrorResponse("Address is null", "Address is null");
    		response.setErrors(err);
    		response.setStatus(StatusCode.ERROR.name());
		} else {
			response.setData(orderModel);
		}
		
		return CommonUtils.getJson(response);
    
    }
    
    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() throws IOException {

       // List<City> cities = (List<City>) cityService.findAll();
    	
        ByteArrayInputStream bis = orderService.citiesReport("india");
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
}
