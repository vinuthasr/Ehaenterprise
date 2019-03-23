package com.elephant.service.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.elephant.config.PaypalPaymentIntent;
import com.elephant.config.PaypalPaymentMethod;
import com.elephant.dao.payment.PaymentDao;
import com.elephant.domain.payment.PaymentDomain;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalService {

	@Autowired
	private APIContext apiContext;
	
	@Autowired
	PaymentDao pd;
	
	//@Autowired
	
	public Payment createPayment(
			Double total, 
			String currency, 
			PaypalPaymentMethod method, 
			PaypalPaymentIntent intent, 
			String description, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));
		//amount.setTotal(total);
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setCity("Boston");
		shippingAddress.setLine1("250 Beacon St #5");
		shippingAddress.setPostalCode("02116");
		shippingAddress.setState("MA");
		shippingAddress.setCountryCode("US");
		shippingAddress.setRecipientName("MIGUEL CAREY");
		
		Item item=new Item();
		item.setPrice("25.00");
		item.setQuantity("2");
		item.setSku("123456789");
		item.setCurrency(currency);
		
		
       ItemList itemList=new ItemList();
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
		itemList.setShippingAddress(shippingAddress);
		
		
		
	    List<Item>items=new ArrayList<>();
	    items.add(item);
		
		itemList.setItems(items);
		transaction.setItemList(itemList);
	
		
		
		
		

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());
	

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
	
		
	
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		

		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		
		return payment.execute(apiContext, paymentExecute);
	}

	public void add(Payment payment) {
		// TODO Auto-generated method stub
		PaymentDomain pm= new PaymentDomain();
		
		pm.setTxnId(payment.getId());
		pm.setStatus(payment.getState());
		pm.setPayJson(payment.toString());
		System.out.println(payment.toString());
		
		//pm.setStatus(status);
		pd.save(pm);
		
		
		
	}

	public void update(Payment payment) {
		
		PaymentDomain pm =pd.findByTxnId(payment.getId());
		pm.setStatus(payment.getState());
		// TODO Auto-generated method stub
		
		pm.setPayJson(payment.toString());
	
		pd.saveAndFlush(pm);
		
	}
}
