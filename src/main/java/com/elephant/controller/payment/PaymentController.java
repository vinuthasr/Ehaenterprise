package com.elephant.controller.payment;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.config.PaypalPaymentIntent;
import com.elephant.config.PaypalPaymentMethod;
import com.elephant.constant.Constants;
import com.elephant.service.payment.PaypalService;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.URLUtils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins= {"https://eha-admin-app.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com"})
public class PaymentController {
	
	public static final String PAYPAL_SUCCESS_URL = "v1/pay/success";
	public static final String PAYPAL_CANCEL_URL = "v1/pay/cancel";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PaypalService paypalService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public  @ResponseBody String pay(@RequestParam("addressId")long addressId,
					 @RequestParam("paymentDesc") String paymentDesc,		
					 @RequestParam("email") String email ,HttpServletRequest request) throws Exception{
		String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
		String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
		try {
			Payment payment = paypalService.createPayment(
					addressId , 
					email,
					Constants.CURRENCY, 
					PaypalPaymentMethod.paypal, 
					PaypalPaymentIntent.sale,
					paymentDesc, 
					cancelUrl, 
					successUrl);
			paypalService.add(payment);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					return CommonUtils.getJson("redirect:" + links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return CommonUtils.getJson("redirect:/");
	}

	@RequestMapping(method = RequestMethod.GET, value = "pay/cancel")
	public @ResponseBody String cancelPay(){
		return CommonUtils.getJson("cancel");
	}

	@RequestMapping(method = RequestMethod.GET, value = "pay/success")
	public @ResponseBody String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				paypalService.update(payment);
				return CommonUtils.getJson("success");
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return CommonUtils.getJson("redirect:/");
	}
	
}
