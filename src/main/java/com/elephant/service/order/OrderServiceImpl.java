package com.elephant.service.order;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.dao.address.AddressDaoRepository;
import com.elephant.dao.cartitem.CartItemDao;
//import com.elephant.dao.cart.CartDaoRepository;
import com.elephant.dao.cartitem.CartItemDaoRepository;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.order.OrderDao;
import com.elephant.dao.order.OrderDaoRepository;
import com.elephant.dao.orderdetail.OrderDetailRepository;
import com.elephant.dao.uploadproduct.ProductRepository;
import com.elephant.domain.address.AddressDomain;
//import com.elephant.domain.cart.CartDomain;
import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.courier.CourOrderDetDomain;
import com.elephant.domain.courier.PickupRequestDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.order.OrderDomain;
import com.elephant.domain.orderdetail.OrderDetailDomain;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.mapper.order.OrderMapper;
import com.elephant.mapper.order.PickupReqMapper;
import com.elephant.mapper.orderdetail.OrderDetailMapper;
import com.elephant.model.address.AddressModel;
import com.elephant.model.courier.CourierOrderDetModel;
import com.elephant.model.courier.CourierOrderModel;
import com.elephant.model.courier.CourierOrderResponse;
import com.elephant.model.courier.PickupLocation;
import com.elephant.model.courier.PickupReqModel;
import com.elephant.model.courier.Shipments;
import com.elephant.model.mail.Mail;
import com.elephant.model.order.OrderModel;
import com.elephant.model.orderdetail.OrderDetailModel;
import com.elephant.model.payment.PaymentModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.address.AddressService;
import com.elephant.service.customer.CustomerService;
import com.elephant.service.customer.EmailService;
//import com.elephant.service.customer.CustomerServiceImpl.EmailAuth;
//import com.elephant.service.invoice.InvoiceService;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.DateUtility;
import com.elephant.utils.SmtpMailSender;
/*-----------------------pdf----------------------------*/
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/*-------------------------------------------------------*/

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	OrderDetailMapper orderDetailMapper;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	/*@Autowired
	CartDaoRepository cartDaoRepository;*/
	
	@Autowired
	SmtpMailSender smtpMailSender;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CartItemDaoRepository cartItemDaoRepository;
	
	@Autowired
	OrderDaoRepository orderDaoRepository;
	
	/*@Autowired
	InvoiceService invoiceService;*/
	
	@Autowired
	CustomerRepository customerRepository;
	//@Override
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressDaoRepository addressDaoRepository;
	
	@Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
	
	@Autowired
	CartItemDao cartItemDao; 
	
	@Autowired
	PickupReqMapper pickupReqMapper;
	
	private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
	
	
	public Response createOrder( PaymentModel paymentModel) throws IOException {
		Response response=CommonUtils.getResponseObject("Order creation");
		
		try {
		//OrderModel orderModel=new OrderModel();
		OrderDomain orderDomain =new OrderDomain();
		//BeanUtils.copyProperties(orderModel, orderDomain);
		/*---------------Get Customer by EmailId--------------------*/
		CustomerDomain customerDomain =customerRepository.findByEmail(paymentModel.getEmail());
		/*---------------Get Cart by Customer----------------------*/
		//CartDomain cartDomain=customerDomain.getCartDomain();
		/*---------------Get Address by Customer-------------------*/
		AddressDomain addressDomain=addressDaoRepository.findByAddressId(paymentModel.getAddressId());
		
		
		if((customerDomain ==null) ||  ((customerDomain.getCartItemDomain())==null) || (addressDomain ==null) ) {
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage4("customer / cart/ cart item /address should not be null");
			return response;
			//throw new IOException("customer / cart/ cart item /address should not be null");
			
		}
		
		
		
		/*------------------------Checking weather paymentMode is COD/PayPal---------------------------------*/

		if(paymentModel.getPaymentMode().equals(Constants.CASH_ON_DELIVERY))
		{
		orderDomain.setPaymentMode(paymentModel.getPaymentMode());
		orderDomain.setTransactionId(CommonUtils.generateRandomId());
		
		
		}
		else if(paymentModel.getPaymentMode().equals(Constants.PAYPAL) || paymentModel.getPaymentMode().equals(Constants.PAYUMONEY)) {
			orderDomain.setPaymentMode(paymentModel.getPaymentMode());
			orderDomain.setTransactionId(paymentModel.getTransactionId());
		}
		else 
		{
			throw new IOException("Spelling of COD/PayPal is wrong  Or Payment Mode is Wrong");
		}
		/*---------------------------------------------------------------------------------------------------*/
		orderDomain.setAddressDomain(addressDomain);
		orderDomain.setCustomerDomain(customerDomain);
		orderDomain.setOrderNumber(CommonUtils.generateRandomId());
		
		List<CartItemDomain> listCartItemDomain=customerDomain.getCartItemDomain();
		double grandtotal=0;
		for(CartItemDomain cartItemDomain:listCartItemDomain) {
			ProductDomain productDomain=cartItemDomain.getProduct();
			grandtotal+= (productDomain.getPrice()-((productDomain.getPrice()*productDomain.getDiscount()/100)))*cartItemDomain.getQuantity();
		}
		orderDomain.setOrderPrice(grandtotal);
		orderDomain.setOrderStatus(Constants.ORDER_CONFORMATION);
		orderDomain.setOrderDate(new Date());
		orderDomain.setCustomerName(customerDomain.getCustomerName());
		orderDomain.setCustomerEmail(customerDomain.getEmail());
		orderDomain.setCustomerMobileNumber(customerDomain.getMobileNumber());

		OrderDomain orderDomain1=orderDaoRepository.save(orderDomain);
		response.setMessage1("Order Creation is Successfull");
		
		/*-------------------------Clear Cart amount after order Confirmation-------------------------------*/
		//cartDomain.setGrandtotal(0);
		//cartDaoRepository.save(cartDomain);
		/*--------------------------------------------------------------------------------------------------*/
		
		/* ------------------------------Invoice Creating to Customer---------------------------------------
		invoiceService.generateInvoice(orderDomain);
		response.setMessage2("Invoice Creation is Successfull");
		System.out.println("Invoice is Generated");
		--------------------------------------------------------------------------------------------------*/
		
		/* -----------------------------dump cartItem to Order detail---------------------------------------*/ 
		/*------------------------------------------&&------------------------------------------------------*/
		/*----------------------------Product In stock should decrease after order is conformed-------------*/
		ProductDomain productDomain = null;
		OrderDetailDomain orderDetailDomain = null;
		List<CartItemDomain> cartItemList = customerDomain.getCartItemDomain();
		Shipments shipmentsList[] = new Shipments[cartItemList.size()];
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		int count=0;
		for(CartItemDomain cartItemDomain: cartItemList) {
		
			productDomain=cartItemDomain.getProduct();
			orderDetailDomain=new OrderDetailDomain();
			shipmentsList[count] = new Shipments();
			
			orderDetailDomain.setOrderdetailId(CommonUtils.generateRandomId());
			orderDetailDomain.setProductSku(productDomain.getSku());
			orderDetailDomain.setProductImagePath(productDomain.getMainImageUrl());
			orderDetailDomain.setProductName(productDomain.getCollectionDesc());
			orderDetailDomain.setProductQuantity(cartItemDomain.getQuantity());
			orderDetailDomain.setProductAmount((productDomain.getPrice()-((productDomain.getPrice()*productDomain.getDiscount()/100)))*cartItemDomain.getQuantity());
			orderDetailDomain.setOrderDomain(orderDomain);
			
			//Shipments - start - Courier third party api
			
			shipmentsList[count].setReturn_name("EHAENTERPRISES SURFACE");
			shipmentsList[count].setReturn_pin("560079");
			shipmentsList[count].setReturn_city("Bangalore");
			shipmentsList[count].setReturn_phone("8904648040");
			shipmentsList[count].setReturn_add("No 23, Magadi Main Rd, Govindaraja Nagar Ward, Pete Channappa Industrial Estate, Kamakshipalya");
			shipmentsList[count].setReturn_state("Karnataka");
			shipmentsList[count].setReturn_country("India");
			shipmentsList[count].setOrder(orderDetailDomain.getOrderdetailId());
			shipmentsList[count].setPhone(String.valueOf(orderDomain.getCustomerMobileNumber()));
			shipmentsList[count].setProducts_desc(orderDetailDomain.getProductName());
			if(orderDomain.getPaymentMode().equalsIgnoreCase(Constants.CASH_ON_DELIVERY)) {
				shipmentsList[count].setCod_amount(String.valueOf(orderDomain.getOrderPrice()));
			} else {
				shipmentsList[count].setCod_amount("0.0");
			}
			shipmentsList[count].setName(orderDomain.getCustomerName());
			shipmentsList[count].setCountry(addressDomain.getCountry());
			shipmentsList[count].setOrder_date(simpleDateFormat.format(new Date()));
			shipmentsList[count].setTotal_amount(String.valueOf(orderDomain.getOrderPrice()));
			shipmentsList[count].setAdd(addressDomain.getAddressline1()+" "+addressDomain.getAddressline2()+" " +addressDomain.getAddressline3());
			shipmentsList[count].setPin(addressDomain.getPincode());
			shipmentsList[count].setQuantity(String.valueOf(orderDetailDomain.getProductQuantity()));
			
			if(orderDomain.getPaymentMode().equalsIgnoreCase(Constants.CASH_ON_DELIVERY)) { 
				shipmentsList[count].setPayment_mode("COD");
			} else {
				shipmentsList[count].setPayment_mode("Prepaid");  // Payment mode = Pickup  -> if it is created for reverse flow -> for return
			}
			shipmentsList[count].setState(addressDomain.getState());
			shipmentsList[count].setCity(addressDomain.getCity());
			shipmentsList[count].setClient("EHAENTERPRISES SURFACE");
			
			//Shipments - End
			
			//ProductDomain productDomain=productRepository.findByProductId(orderDetailDomain.getProductId());
			if(productDomain.getInStock() < orderDetailDomain.getProductQuantity()) {
				response.setStatus(StatusCode.ERROR.toString());
				response.setMessage3(productDomain.getSku() +" stock is not available");
				return response;
			} else {
				orderDetailRepository.save(orderDetailDomain);
				productDomain.setInStock(productDomain.getInStock()-orderDetailDomain.getProductQuantity());
				if(productDomain.getInStock() == 0) {
					productDomain.setActive(false);
				}
				productRepository.saveAndFlush(productDomain);
				cartItemDao.deleteCartItem(cartItemDomain.getCartItemId());
			}
			
			count++;
		}
		response.setMessage3("Cart Items are dumped into Order details");
		
		/*-------------------------------------------------------------------------------------------------*/
		response = courierCreateOrderAPI(shipmentsList,orderDomain.getOrderNumber(),orderDomain.getCustomerEmail());  //Courier Third party api
		
		 Mail mail = new Mail();
         mail.setFrom("ehauiele@gmail.com");
         mail.setTo(orderDomain.getCustomerEmail());
         mail.setSubject("Sending Email with Thymeleaf HTML Template Example");
   
        List<OrderDetailDomain> kkk=orderDaoRepository.findByOrderId(orderDomain1.getOrderId()).getOrderDetailDomain();
      
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("name",kkk);
        //context.setVariable("size",kkk.size());
        String html = templateEngine.process("order", context);
        System.out.println(html);
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        emailSender.send(message);
    	response.setMessage("Order Confirmation Mail sent Successfull");
    	System.out.println("Order Confirmation Mailsent  successfull");
		return response;
		}catch(MailSendException exce) {
			//response.setStatus(StatusCode.ERROR.name());
			response.setMessage("We are unable to send email due to gmail port issue. Please refer the order id: ");
		}
		catch(Exception ex) {
			System.out.println("Exception in ordercreate status"+ex);
	   }
		
		return response;

	}	
	
	
	private Response courierCreateOrderAPI(Shipments[] shipmentsList,String orderNumber,String customerEmail) {
		Response response=CommonUtils.getResponseObject("Create courier order");
		final String uri = Constants.DELHIVERY_URL+"api/cmu/create.json";
		String authToken = Constants.DELHIVERY_TOKEN;
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			CourierOrderModel courierOrderModel = new CourierOrderModel();
			courierOrderModel.setShipments(shipmentsList);
			
			PickupLocation pickupLocation = new PickupLocation();
			pickupLocation.setAdd("No 23, Magadi Main Rd, Govindaraja Nagar Ward, Pete Channappa Industrial Estate, Kamakshipalya");
			pickupLocation.setCity("Bengaluru");
			pickupLocation.setPin("560079");
			pickupLocation.setPhone("8904648040");
			pickupLocation.setState("Karnataka");
			pickupLocation.setCountry("India");
			pickupLocation.setName("EHAENTERPRISES SURFACE");
			
			courierOrderModel.setPickup_location(pickupLocation);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Token " + authToken);
			
			String body = "format=json&data="+CommonUtils.getJson(courierOrderModel);
			
			HttpEntity<String> request = new HttpEntity<String>(body,headers);

			ResponseEntity<CourierOrderResponse> result = restTemplate.postForEntity(uri, request, CourierOrderResponse.class);
			
			if(result.getBody().getSuccess().equalsIgnoreCase("false")) {
				int packageCount = Integer.parseInt(result.getBody().getPackage_count());
				
				for (int i = 0; i < packageCount; i++) {
					response.setMessage(result.getBody().getPackages()[i].getRemarks()[0]);
				}
			} else {
				result.getBody().setCreatedDate(new Date());
				result.getBody().setModifiedDate(new Date());
				
				saveCourierDetails(result.getBody(),orderNumber,customerEmail);
			}
			
			
		} catch (Exception e) {
			System.out.println("Exception: " +e);
		}
		return response;
	}

	private void saveCourierDetails(CourierOrderResponse result,String orderNumber,String customerEmail) {
		try {
			CourOrderDetDomain courOrderDetDomain = null;
			if(result != null) {
				int packageCount = Integer.parseInt(result.getPackage_count());
				for (int count = 0; count < packageCount; count++) {
					courOrderDetDomain = new CourOrderDetDomain();
					courOrderDetDomain.setCourierOrderId(CommonUtils.generateRandomId());
					courOrderDetDomain.setCreatedDate(new Date());
					courOrderDetDomain.setModifiedDate(new Date());
					courOrderDetDomain.setOrderDetailId(result.getPackages()[count].getRefnum());
					courOrderDetDomain.setOrderId(orderNumber);
					courOrderDetDomain.setSortCode(result.getPackages()[count].getSort_code());
					courOrderDetDomain.setStatus("Manifested");
					courOrderDetDomain.setUploadWbn(result.getUpload_wbn());
					courOrderDetDomain.setWayBill(result.getPackages()[count].getWaybill());
					courOrderDetDomain.setCustomerEmail(customerEmail);
					orderDao.saveCourierDetails(courOrderDetDomain);
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public List<OrderModel> getAllCustomerOrders(String email) {
		try {
		CustomerDomain customerDomain =customerRepository.findByEmail(email);
		List<OrderDomain> orderDomain=customerDomain.getOrderDomain();
			
		return orderMapper.entityList(orderDomain);
		}catch(Exception ex) {
			System.out.println("Exception in getAllCustomerOrders"+ex);
		}
		return null;
	}

	
	@Override
	public List<OrderModel> getAllOrders() {
		try {
		List<OrderDomain> orderDomain=orderDao.getAllOrders();
		return orderMapper.entityList(orderDomain);
		}catch(Exception ex) {
			System.out.println("Excpetion in getAllOrders"+ex);
		}
		return null;
	}

	@Override
	public double totalOrdersAmountPerDay(Date orderDate) {
		double totalOrderAmountPerDay=0;
		List<OrderDomain> orderDomainOrderPerDay=orderDao.getOrdersByDate(orderDate);
		for(OrderDomain orderDomain:orderDomainOrderPerDay) {
			totalOrderAmountPerDay+=orderDomain.getOrderPrice();
		}
		return totalOrderAmountPerDay;
	}

	@Override
	public Response cancelOrder(long orderId) {
		Response response=CommonUtils.getResponseObject("Cancel Order");
		
		try {
			
			/*-----------------------------------Cancellation of Order-----------------------------------------*/
			OrderDomain orderDomain=orderDaoRepository.findByOrderId(orderId);
			orderDomain.setOrderStatus(Constants.CANCEL_ORDER);
			orderDaoRepository.saveAndFlush(orderDomain);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("Order Cancellation is Successfull");
			System.out.println("Order Cancellation is successfull");
			/*-------------------------------------------------------------------------------------------------*/
			
			/** Cancel order in Delhivery Courier service */
			
			cancelCourierServiceOrder(orderDomain);
			/*---------------------------------------------------------*/
			
			/*------------------------------Products In stock should automatically increase after Order Cancellation----*/
			for(OrderDetailDomain orderDetailDomain:orderDomain.getOrderDetailDomain()) {
				//ProductDomain productDomain=productRepository.findBySku(orderDetailDomain.getProductSku());
				ProductDomain productDomain=productRepository.findBySku(orderDetailDomain.getProductSku());
				productDomain.setInStock(productDomain.getInStock()+orderDetailDomain.getProductQuantity() );
				productRepository.saveAndFlush(productDomain);
			}
			/*-------------------------------------------------------------------------------------------------*/			
			
			
			/*-----------------------------Email Cancel Order Confirm to Customer-------------------------------*/
			//Setting up configurations for the email connection to the Google SMTP server using TLS
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        //Establishing a session with required user details
	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("shekhar.reddy2011@gmail.com", "venkataramireddy");
	            }
	        });

	        
	        
	      //Creating a Message object to set the email content
	        MimeMessage msg = new MimeMessage(session);
	        //Storing the comma separated values to email addresses
	        String to =orderDomain.getCustomerDomain().getEmail() ;
	        /*Parsing the String with default delimiter as a comma by marking the boolean as true and storing the email
	        addresses in an array of InternetAddress objects*/
	        InternetAddress[] address = InternetAddress.parse(to, true);
	        //Setting the recipients from the address variable
	        msg.setRecipients(Message.RecipientType.TO, address);
	        String timeStamp = new SimpleDateFormat("   dd/MM/yyy hh:mm:ss").format(new Date());
	        msg.setSubject("Eha Enterprises"+"\n\n"+"Order Cancellation " +"\n"+" Date:-"+ timeStamp);
	        msg.setSentDate(new Date());
	        
	        
	        /*String htmlMsg="<h3>Im testing  a HTML email</h3>"
	                +"<img src='https://giphy.com/gifs/artists-on-tumblr-ganesh-chaturthi-YVbFW9JoU5v1K'>";
	        
	        msg.setContent( htmlMsg, "text/html");*/
	        msg.setText("Dear "+orderDomain.getCustomerDomain().getCustomerName()+ "\n\n"
	        			+ "Your Order is Cancelled for  Order Number :"+ orderDomain.getOrderNumber()+ "\n\n"
	        			+ "Thank you"+ "\n\n"
	        			+ "Regards"+"\n"
	        			+ "Eha Enterprices"
	        			);
	        
	        msg.setHeader("XPriority", "1");
	        Transport.send(msg);
	 /*----------------------------------------------------------------------------------------------------------*/      
	        System.out.println("Order Cancellation Mail has been sent successfully");
			response.setMessage1(" Order Cancellation Mail has been sent successfully");
			return response;
		}catch(Exception ex) {
			ErrorObject err=CommonUtils.getErrorResponse("Cancel Order is not successfull", "Cancel Order is not successfull");
			response.setErrors(err);
			response.setErrors(ex.getMessage());
			System.out.println("Exception in cancel order"+ex);
		
		}
		return response;
		
	}

	@SuppressWarnings("rawtypes")
	private Response cancelCourierServiceOrder(OrderDomain orderDomain) {
		Response response=CommonUtils.getResponseObject("Cancel Courier Service Order");
		final String uri = Constants.DELHIVERY_URL+"api/p/edit";
		String authToken = Constants.DELHIVERY_TOKEN;
		RestTemplate restTemplate = new RestTemplate();
		try {
			List<OrderDetailDomain> orderDetailDomainList = orderDomain.getOrderDetailDomain();
			String wayBillNo = null;
			if(null != orderDetailDomainList) {
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				headers.add("Authorization", "Token " + authToken);
				HttpEntity<Map> request = null;
				ResponseEntity<String> result = null;
				Map<String,String>  cancelOrderMap = new HashMap<String, String>();
				for(OrderDetailDomain detailDomain:orderDetailDomainList) {
					wayBillNo =  orderDao.getWayBillNoByDetId(detailDomain.getOrderdetailId());
					cancelOrderMap.put("waybill", wayBillNo);
					cancelOrderMap.put("cancellation", "true");
					
					request = new HttpEntity<Map>(cancelOrderMap,headers);	
					result = restTemplate.postForEntity(uri, request, String.class);
					
					JSONObject jsonObject = new JSONObject(result.getBody());
					String status = jsonObject.get("status").toString();
					if(status.equalsIgnoreCase("true")) {
						response.setMessage("Cancellation successfull");
						response.setStatus(StatusCode.SUCCESS.name());
					}
				}
			}
			
		}catch (Exception e) {
			throw new RuntimeException("Exception: " +e);
		}
		return response;
	}
	
	@Override
	public List<OrderModel> getOrdersByDate(Date orderDate) {
	

		try {
			List<OrderDomain> orderDomain=orderDao.getOrdersByDate(orderDate);
			return orderMapper.entityList(orderDomain);
			}catch(Exception ex) {
				System.out.println("Excpetion in getOrderByDate"+ex);
			}
			return null;
	}

	@Override
	public List<OrderModel> totalOrdersFromDateToParticularDate(Date fromDate, Date toDate) {
	
		try {
			List<OrderDomain> orderDomain=orderDao.totalOrdersFromDateToParticularDate(fromDate,toDate);
			return orderMapper.entityList(orderDomain);
			}
		catch(Exception ex) {
				System.out.println("Excpetion in totalOrdersFromDateToParticularDate"+ex);
			}
			return null;
	}

	@Override
	public Response deleteAllOrders() {
		Response res=CommonUtils.getResponseObject("Delete All Orders");
	
		try {
		 orderDaoRepository.deleteAll();
		 res.setStatus(StatusCode.SUCCESS.name());
		 return res;
		}
		catch(Exception ex) {
			res.setStatus(StatusCode.ERROR.name());	
			System.out.println("Exception in deleteAll Orders"+ex);
		}
		return res;
	}

	@Override
	public List<OrderDetailModel> getOrderDetailsByOrderId(long orderId) {
		
		try {
		//CustomerDomain customerDomain=customerRepository.findByEmail(email);
		OrderDomain orderDomain=orderDaoRepository.findByOrderId(orderId);
		List<OrderDetailDomain> orderDetailDomain=orderDomain.getOrderDetailDomain();
		//List<OrderDomain> orderDomain=customerDomain.getOrderDomain();
		//List<OrderDetailDomain> orderDetailDomain=orderDomain.get(2).getOrderDetailDomain();
		return orderDetailMapper.entityList(orderDetailDomain);
		}catch(Exception ex) {
			System.out.println("Exception in getOrderDetailsByCustomer"+ex);
		}
		
		return null;
	}

	
	public AddressModel getAddressByOrderId(long orderId) {
		
		OrderDomain orderDomain=orderDaoRepository.findByOrderId(orderId);
		AddressDomain addressDomain=orderDomain.getAddressDomain();
		AddressModel addressModel=new AddressModel();
		BeanUtils.copyProperties(addressDomain, addressModel);
		return addressModel;
		
	}

	@Override
	public Response updateOrderStatus(OrderModel orderModel) {
		
		Response response=CommonUtils.getResponseObject("Update order status");
		try {	
		OrderDomain orderDomain= new OrderDomain();
		BeanUtils.copyProperties(orderModel, orderDomain);
		OrderDomain orderDomainUpdated=orderDaoRepository.findByOrderId(orderDomain.getOrderId());
		orderDomainUpdated.setOrderStatus(orderDomain.getOrderStatus());	
		orderDaoRepository.saveAndFlush(orderDomainUpdated);
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage("Order Status Saved Successfull");
		
		//Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        //Establishing a session with required user details
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("shekhar.reddy2011@gmail.com", "venkataramireddy");
            }
        });

        
        
      //Creating a Message object to set the email content
        MimeMessage msg = new MimeMessage(session);
        //Storing the comma separated values to email addresses
        String to =orderDomainUpdated.getCustomerDomain().getEmail() ;
        /*Parsing the String with default delimiter as a comma by marking the boolean as true and storing the email
        addresses in an array of InternetAddress objects*/
        InternetAddress[] address = InternetAddress.parse(to, true);
        //Setting the recipients from the address variable
        msg.setRecipients(Message.RecipientType.TO, address);
        String timeStamp = new SimpleDateFormat("   dd/MM/yyy hh:mm:ss").format(new Date());
        msg.setSubject("Order Update from Eha Enterprises for Order Number: " +  orderDomainUpdated.getOrderNumber() +" Date:-"+ timeStamp);
        msg.setSentDate(new Date());
        
        
        /*String htmlMsg="<h3>Im testing  a HTML email</h3>"
                +"<img src='https://giphy.com/gifs/artists-on-tumblr-ganesh-chaturthi-YVbFW9JoU5v1K'>";
        
        msg.setContent( htmlMsg, "text/html");*/
        msg.setText("Dear "+orderDomainUpdated.getCustomerDomain().getCustomerName()+ "\n\n"
        			+ "Your Order Number bearing:"+ orderDomainUpdated.getOrderNumber()+" status is -" + orderDomainUpdated.getOrderStatus()+"\n\n"
        			+ "Thank you"+ "\n\n"
        			+ "Regards"+"\n"
        			+ "Eha Enterprices"
        			);
        
        msg.setHeader("XPriority", "1");
        Transport.send(msg);
        System.out.println("Order Update mail  sent successfull");

        	response.setMessage1("Order Update mail  sent successfull");
            
            return response;
		}catch(Exception ex) {
    			System.out.println("Exception in update status"+ex);
    			response.setErrors(ex.getMessage());
    			response.setStatus(StatusCode.ERROR.name());
    			
    	}
    		
    		return response;
            
	}

	@Override
	public OrderModel getOrderByOrderId(long orderId) {
		try {
			OrderDomain orderDomain=orderDaoRepository.findByOrderId(orderId);
			OrderModel orderModel=new OrderModel();
			BeanUtils.copyProperties(orderDomain, orderModel);
			return orderModel;
		}catch(Exception ex) {
			System.out.println("Exception in getOrderByOrderId"+ex );
		}
		return null;
	}
	
	public  ByteArrayInputStream citiesReport(String country) {

		List<AddressDomain> addressDomainList=addressDaoRepository.findAllByCountry(country);
		
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{1, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("addressId", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("addressline1", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("addressline2", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (AddressDomain addressDomain : addressDomainList) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(addressDomain.getAddressId()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(addressDomain.getAddressline1()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(addressDomain.getAddressline2())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
            
            document.close();
            
        } catch (DocumentException ex) {
        System.out.println("Exception in pdf service"+ex);
            //Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
	
	public Integer getNoOfOrdersReceived() {
		return orderDao.getNoOfOrdersReceived();
	}
	
	public Double getTotalEarnings() {
		return orderDao.getTotalEarnings();
	}

	@Override
	public List<Map<String, Object>> getSalesReport() {
		// TODO Auto-generated method stub
		return orderDao.getSalesReport();
	}

	@Override
	public List<Map<String, Object>> getCourierOrderDetails(String status) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> courierOrderDetList = null;
		if(null != status) {
			courierOrderDetList = orderDao.getCourierOrderDetails(status);
		} else {
			throw new RuntimeException("Status cannot be empty");
		}
		return courierOrderDetList;
	}

	@Override
	public Response updateCourierOrderStatus(CourierOrderDetModel courierOrderDetModel) {
		// TODO Auto-generated method stub
		Response response=CommonUtils.getResponseObject("Update order status");
		String[] status = new String[2];
		try {
			if(null != courierOrderDetModel) {
				String courierOrderId = courierOrderDetModel.getCourierOrderId();
				if(null != courierOrderId) {
					CourOrderDetDomain courOrderDetDomain = new CourOrderDetDomain();
					courOrderDetDomain = orderDao.getCourOrderDetById(courierOrderId);
					//To get order status and update in the database	
					status = trackCourierOrderDetails(courOrderDetDomain);  
					
					if(null != status) {
						if(status[1].equalsIgnoreCase("Seller cancelled the order")) {
							courOrderDetDomain.setStatus("Cancelled");
						} else {
							courOrderDetDomain.setStatus(status[0]);
						}
						
						courOrderDetDomain.setModifiedDate(new Date());
						response = orderDao.updateCourierOrderStatus(courOrderDetDomain);
					}
				} else {
					throw new RuntimeException("Enter courier order id");
				}
			} else {
				throw new RuntimeException("Enter the details");
			}
		} catch (Exception e) {
			throw new RuntimeException("Exception " +e);
		}
		
		return response;
	}
	
	
	private String[] trackCourierOrderDetails(CourOrderDetDomain courOrderDetDomain) {
		final String uri = Constants.DELHIVERY_URL+"api/packages/json/";
		String authToken = Constants.DELHIVERY_TOKEN;
		String[] statusStr = new String[2];
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			if(null != courOrderDetDomain) {
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", "application/json");
				
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
				        .queryParam("token", authToken)
				        .queryParam("waybill", courOrderDetDomain.getWayBill())
				        .queryParam("verbose", 0);

				HttpEntity<?> entity = new HttpEntity<>(headers);				
				HttpEntity<String> result = restTemplate.exchange(
				        builder.toUriString(), 
				        HttpMethod.GET, 
				        entity, 
				        String.class);

				JSONObject jsonObject = new JSONObject(result.getBody());
				JSONArray shipmentDataArray = jsonObject.getJSONArray("ShipmentData");
				JSONObject shipmentObject = shipmentDataArray.getJSONObject(0).getJSONObject("Shipment");
				statusStr[0] = shipmentObject.getJSONObject("Status").getString("Status");
				statusStr[1] = shipmentObject.getJSONObject("Status").getString("Instructions");
				
			}
		}catch (Exception e) {
			throw new RuntimeException("Exception " +e);
		}
		
		return statusStr;
	}

	@Override
	public Response createPickupRequest(PickupReqModel pickupReqModel) {
		Response response=CommonUtils.getResponseObject("Create Pick up request");
		
		PickupRequestDomain pickupRequestDomain=new PickupRequestDomain();
		BeanUtils.copyProperties(pickupReqModel, pickupRequestDomain);
		pickupRequestDomain.setCreatedDate(new Date());
		pickupRequestDomain.setModifiedDate(new Date());
		
		String pickupDate = DateUtility.getDateByStringFormat(pickupReqModel.getPickupDateDt(),DateUtility.DATE_FORMAT_YYYYMMDD);
		pickupRequestDomain.setPickup_date(pickupDate);
		
		response  = createCourierPickupReq(pickupRequestDomain);  //To  save in third party url - Delhivery api
		
		return response;
	}
	
	private Response createCourierPickupReq(PickupRequestDomain pickupRequestDomain) {
		Response response=CommonUtils.getResponseObject("Create Courier Pick up request");
		final String uri = Constants.DELHIVERY_URL+"fm/request/new/";
		String authToken = Constants.DELHIVERY_TOKEN;
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Token " + authToken);
			
			HttpEntity<PickupRequestDomain> request = new HttpEntity<PickupRequestDomain>(pickupRequestDomain,headers);

			ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
			JSONObject jsonObject = new JSONObject(result.getBody());
			if(jsonObject.has("success")) {
				if(jsonObject.get("success").toString().equalsIgnoreCase("false")) {
					String message = jsonObject.getJSONObject("data").get("message").toString();
					response.setStatus(StatusCode.ERROR.name());
					response.setMessage(message);
				}
			} else {
				int pickupId = Integer.parseInt(jsonObject.get("pickup_id").toString());
				String incomingCenterName = jsonObject.get("incoming_center_name").toString();
				pickupRequestDomain.setPickup_id(pickupId);
				pickupRequestDomain.setIncoming_center_name(incomingCenterName);//To save in local db
				response = orderDao.createPickupRequest(pickupRequestDomain);
			}
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	@Override
	public List<PickupReqModel> getPickupReqDetails(Date fromDate, Date toDate) {
		List<PickupReqModel> pickupReqList = null;
		try {
			if(null != fromDate && null != toDate) {
				List<PickupRequestDomain> pickupReqDomainList = orderDao.getPickupReqDetails(fromDate, toDate);
				if(pickupReqDomainList != null) {
					pickupReqList = pickupReqMapper.entityList(pickupReqDomainList);
				}
			} else {
				throw new RuntimeException("Enter from date and to date");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return pickupReqList;
	}
	
	
}




