package com.elephant.controller.order;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.model.address.AddressModel;
import com.elephant.model.courier.CourierOrderDetModel;
import com.elephant.model.courier.PickupReqModel;
import com.elephant.model.order.OrderModel;
import com.elephant.model.orderdetail.OrderDetailModel;
import com.elephant.model.payment.PaymentModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.order.OrderService;
import com.elephant.utils.CommonUtils;

@RestController
@RequestMapping(value="/v1/Orders")
@CrossOrigin(origins= {Constants.ADMIN_URL,Constants.CUSTOMER_URL,Constants.LOCALHOST_URL,"https://sandboxsecure.payu.in/_payment"},allowedHeaders="*")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	
	@RequestMapping(value = "/ordercreate/{email}/{addressId}/{paymentMode}",method=RequestMethod.POST, produces="application/json")
	//@CrossOrigin(origins="https://sheltered-fortress-53647.herokuapp.com",allowedHeaders="*")
	@CrossOrigin(origins="https://sandboxsecure.payu.in/_payment",allowedHeaders="*")
    //public Response createOrder(@RequestParam(value="email")String email, @PathVariable(value="addressId")long addressId ,@RequestParam(value="paymentMode") String paymentMode) throws IOException{
	public Response createOrder(@RequestBody PaymentModel paymentModel)	throws IOException{
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
    
    
    @RequestMapping(value = "/cancelOrder/{orderDetailId}",method=RequestMethod.POST, produces="application/json")
    public Response cancelOrder(@RequestParam(value="orderDetailId")String orderDetailId){
		return orderService.cancelOrder(orderDetailId);
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
    		ErrorObject err=CommonUtils.getErrorResponse("No Data found", "No Data found");
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
    
    
    @RequestMapping(value = "/getCourierOrderDetails",method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String getCourierOrderDetails(@RequestParam(value="status") String status){
    	Response res = CommonUtils.getResponseObject("Get Courier Order Details");
    	List<Map<String, Object>> courierOrderDetails=orderService.getCourierOrderDetails(status);
		if (courierOrderDetails.isEmpty() ) {
			ErrorObject err = CommonUtils.getErrorResponse("No Records found", "No Records found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(courierOrderDetails);
		}
		
		return CommonUtils.getJson(res);
    	
    }
    
    
    @RequestMapping(value = "/updateCourierOrderStatus",method=RequestMethod.POST, produces="application/json")
    public @ResponseBody String updateCourierOrderStatus(@RequestBody CourierOrderDetModel courierOrderDetModel){
    	Response res = CommonUtils.getResponseObject("Update Courier Order Details");
    	res=orderService.updateCourierOrderStatus(courierOrderDetModel);
		return CommonUtils.getJson(res);
    }
    
    @RequestMapping(value = "/createPickupReq",method=RequestMethod.POST, produces="application/json")
	public Response createPickupRequest(@RequestBody PickupReqModel pickupReqModel)	throws IOException{
       return orderService.createPickupRequest(pickupReqModel);
	}
    
    @RequestMapping(value = "/getPickupRequestDetails",method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String getPickupRequestDetails(@RequestParam(value="fromDateStr") String fromDateStr,@RequestParam(value="toDateStr") String toDateStr) throws ParseException{
    	Response res = CommonUtils.getResponseObject("Get Pickup Request Details");
    	
    	/*--------------String To Date---------------*/
    	/*  Convert from String to Date*/
    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	Date fromDate = format.parse(fromDateStr);
    	Date toDate=format.parse(toDateStr);
    	/*------------------------------------------*/
    	
    	List<PickupReqModel>  pickupReqList =orderService.getPickupReqDetails(fromDate, toDate);
		if (pickupReqList.isEmpty() ) {
			ErrorObject err = CommonUtils.getErrorResponse("No Records found", "No Records found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(pickupReqList);
		}
		
		return CommonUtils.getJson(res);
    	
    }
    
    @RequestMapping(value = "/getOrderDetails/{fromDateString}/{toDateString}",method=RequestMethod.GET, produces="application/json")
   	public String getOrderDetails(@RequestParam(value="fromDateString")String fromDateString, @RequestParam(value="toDateString")String toDateString) throws ParseException{
   		
       	/*--------------String To Date---------------*/
       	/*  Convert from String to Date*/
//       	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//       	Date fromDate = format.parse(fromDateString);
//       	Date toDate=format.parse(toDateString);
       	/*------------------------------------------*/
       	
    	Response res = CommonUtils.getResponseObject("Get order details");
    	List<Map<String, Object>> orderDetails=orderService.getOrderDetails(fromDateString,toDateString);
		if (orderDetails.isEmpty() ) {
			ErrorObject err = CommonUtils.getErrorResponse("No Records found", "No Records found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orderDetails);
		}
		
		return CommonUtils.getJson(res);
   	} 
    
}
