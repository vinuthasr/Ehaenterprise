package com.elephant.controller.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elephant.constant.*;
import com.elephant.config.PayPalResult;
import com.elephant.config.PayPalSucess;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.model.payment.PaymentModel;
import com.elephant.model.payment.Product;
import com.elephant.service.order.OrderService;
import com.elephant.service.payment.PayPalService1;


@Controller
@RequestMapping("/v1/cart")
@CrossOrigin(origins= {"https://eha-admin-app.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com"})

public class PaymentController1 {
	
	/*static String email="null";
	static long addressid=0;*/
	
	@Autowired
	private PayPalService1 payPalService1; 
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderService orderService;
	
	
	 PaymentModel paymentModel=new PaymentModel();

	@RequestMapping(value="/paypal",method=RequestMethod.GET, produces="application/json")
	public String cartpage(ModelMap modelMap) {
		
	   
	    //paymentModel.setEmail(Email);
		//paymentModel.setAddressId(addressId);
		List<Product> products=new ArrayList<Product>();
		/*------------------Get Total Price from Cart-------------------------------*/
		
		//CustomerDomain customerDomain=customerRepository.findByEmail(Email);
		//List<CartItemDomain> listCartItemDomain=customerDomain.getCartItemDomain();
		double grandtotal=0;
		//for(CartItemDomain cartItemDomain:listCartItemDomain) {
			//ProductDomain productDomain=cartItemDomain.getProduct();
			//grandtotal+= (productDomain.getPrice()-((productDomain.getPrice()*productDomain.getDiscount()/100)))*cartItemDomain.getQuantity();
		//}
		
		products.add(new Product("Total products", 20));
		/*--------------------------------------------------------------------------*/
		
		modelMap.put("products", products);
		modelMap.put("payPalConfig",payPalService1.getPayPalConfig() );
		
		return "paypal";
	}
	
	
	@RequestMapping(value = "success",  method=RequestMethod.GET)
	public String success(HttpServletRequest request) {
		
		try {
			
		PayPalSucess payPalSucess = new PayPalSucess();
		PayPalResult payPalResult = payPalSucess.getPayPal(request, payPalService1.getPayPalConfig());
		/*---------------------Set Payment to PayPal Mode and Get transactionId through PayPalResult Class--------*/
		paymentModel.setPaymentMode(Constants.PAYPAL);
		paymentModel.setTransactionId(payPalResult.getTxn_id());
		/*---------------------------------------------------------------------------------------------------------*/
		orderService.createOrder(paymentModel);

		System.out.println("Order Info");
		System.out.println("First Name: " + payPalResult.getFirst_name());
		System.out.println("Last Name" + payPalResult.getLast_name() );
		System.out.println("Country" + payPalResult.getAddress_country());
		System.out.println("Email" + payPalResult.getPayer_email());
		System.out.println("Country" + payPalResult.getMc_gross());
		}catch(Exception ex) {
			System.out.println("Exception in success"+ex);
		}
		
		return "cart/success";
		}

}
