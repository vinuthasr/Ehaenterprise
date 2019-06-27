package com.elephant.controller.payment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.elephant.config.PaypalPaymentIntent;
import com.elephant.config.PaypalPaymentMethod;
import com.elephant.constant.Constants;
import com.elephant.constant.PaymentMode;
import com.elephant.constant.PaymentStatus;
import com.elephant.constant.StatusCode;
import com.elephant.domain.payment.PUMPaymentDomain;
import com.elephant.model.payment.PaymentCallback;
import com.elephant.model.payment.PaymentDetail;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.payment.PaypalService;
import com.elephant.utils.CommonUtils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/v1")
//@CrossOrigin(origins= {"https://eha-admin-v1.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com","http://media.payumoney.com"})
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	//public static final String PAYPAL_SUCCESS_URL = "v1/pay/success";
	//public static final String PAYPAL_CANCEL_URL = "v1/pay/cancel";
	
	public static final String PAYPAL_SUCCESS_URL = "#/success";
	public static final String PAYPAL_CANCEL_URL = "#/cancel";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PaypalService paypalService;
	
	 @GetMapping
	    public ModelAndView viewPaymentPage() {
	        ModelAndView model = new ModelAndView();
	        model.setViewName("paymentview");
	        return model;
	    }
	/*----Payal----- - Start*/
	@RequestMapping(method = RequestMethod.POST, value = "/pay")
	public  @ResponseBody String pay(@RequestParam("addressId")long addressId,
					 @RequestParam("paymentDesc") String paymentDesc,		
					 @RequestParam("email") String email ,HttpServletRequest request) throws Exception{
		//String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
		//String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
		
		String cancelUrl ="http://localhost:4200"+ "/" + PAYPAL_CANCEL_URL;
		String successUrl ="http://localhost:4200" + "/" + PAYPAL_SUCCESS_URL;
		
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
					//return CommonUtils.getJson("redirect:" + links.getHref());
					return CommonUtils.getJson(links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
			return CommonUtils.getJson(e.getMessage());
		}
		return CommonUtils.getJson("redirect:/");
	}

	@RequestMapping(method = RequestMethod.GET, value = "pay/cancel")
	public @ResponseBody String cancelPay(){
		return CommonUtils.getJson("cancel");
	}

	@RequestMapping(method = RequestMethod.GET, value = "pay/success")
	public @ResponseBody ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
		Map<String,Object> map=new HashMap<>();
		try 
		{
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				paypalService.update(payment);
				map.put("status", "success");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
			}
		} 
		catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		map.put("status", "Something went wrong");
	
		return new ResponseEntity<Map<String,Object> >(map,HttpStatus.BAD_REQUEST);
	}

	  /*----- Paypal end --- */
	
	/* Payumoney -----*/
	
	@PostMapping(path = "/paymentdetails")
    public @ResponseBody PaymentDetail proceedPayment(@RequestParam("email") String email){
		PaymentDetail paymentDetail = paypalService.proceedPayment(email);
		return paymentDetail;
    }

	
    @RequestMapping(path = "/paymentresponse", method = RequestMethod.POST)
    public @ResponseBody String payuCallback(@RequestParam String mihpayid, @RequestParam String status, @RequestParam PaymentMode mode, @RequestParam String txnid, @RequestParam String hash){
    	Response res = CommonUtils.getResponseObject("PUM response");
    	PaymentCallback paymentCallback = new PaymentCallback();
        paymentCallback.setMihpayid(mihpayid);
        paymentCallback.setTxnid(txnid);
        paymentCallback.setMode(mode);
        paymentCallback.setHash(hash);
        paymentCallback.setStatus(status);
        PUMPaymentDomain paymentDomain= paypalService.payuCallback(paymentCallback);
        if(null != paymentDomain) {
        	if(paymentDomain.getPaymentStatus().equals(PaymentStatus.Failed)) {
        		ErrorObject err = CommonUtils.getErrorResponse("Transaction failed", "Transaction failed");
    			res.setErrors(err);
    			res.setStatus(StatusCode.ERROR.name());
        	} else {
        		res.setMessage("Transaction successfull");
        		res.setData(paymentDomain);
        	}
        }
        logger.info("payuCallback: Sent response");
        return CommonUtils.getJson(res);
    }

}
