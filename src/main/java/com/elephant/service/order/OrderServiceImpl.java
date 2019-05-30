package com.elephant.service.order;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.dao.address.AddressDaoRepository;
import com.elephant.dao.cartitem.CartItemDao;
import com.elephant.dao.cartitem.CartItemDaoImpl;
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
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.order.OrderDomain;
import com.elephant.domain.orderdetail.OrderDetailDomain;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.mapper.order.OrderMapper;
import com.elephant.mapper.orderdetail.OrderDetailMapper;
import com.elephant.model.address.AddressModel;
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
		System.out.println(paymentModel.getPaymentMode());
		if(paymentModel.getPaymentMode().equals(Constants.CASH_ON_DELIVERY))
		{
		orderDomain.setPaymentMode(paymentModel.getPaymentMode());
		orderDomain.setTransactionId(CommonUtils.generateRandomId());
		
		
		}
		else if(paymentModel.getPaymentMode().equals(Constants.PAYPAL)) {
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
		for(CartItemDomain cartItemDomain: customerDomain.getCartItemDomain()) {
		
			productDomain=cartItemDomain.getProduct();
			orderDetailDomain=new OrderDetailDomain();
			
			orderDetailDomain.setProductSku(productDomain.getSku());
			orderDetailDomain.setProductImagePath(productDomain.getMainImageUrl());
			orderDetailDomain.setProductName(productDomain.getCollectionDesc());
			orderDetailDomain.setProductQuantity(cartItemDomain.getQuantity());
			orderDetailDomain.setProductAmount((productDomain.getPrice()-((productDomain.getPrice()*productDomain.getDiscount()/100)))*cartItemDomain.getQuantity());
			orderDetailDomain.setOrderDomain(orderDomain);
			
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
		}
		response.setMessage3("Cart Items are dumped into Order details");
		/*-------------------------------------------------------------------------------------------------*/
		
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
    	response.setMessage("Order Conformation Mail sent Successfull");
    	System.out.println("Order Conformation Mailsent  successfull");
		return response;
		}catch(MailSendException exce) {
			//response.setStatus(StatusCode.ERROR.name());
			response.setMessage("We are unable to send email due to gmail port issue. Please click on the below code to validate your email id");
		}
		catch(Exception ex) {
			System.out.println("Exception in ordercreate status"+ex);
	   }
		
		return response;

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
	
	
	
}




