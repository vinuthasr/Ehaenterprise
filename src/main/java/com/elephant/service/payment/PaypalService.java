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
import com.elephant.model.address.AddressModel;
import com.elephant.model.cartitem.CartItemModel;
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
			List<CartItemModel> cartItemModelList, 
			String currency, 
			PaypalPaymentMethod method, 
			PaypalPaymentIntent intent, 
			String description, 
			String cancelUrl, 
			String successUrl,
			AddressModel addressModel,
			String userName) throws PayPalRESTException{
		double total =0.0;
		//amount.setTotal(total);
		
		ShippingAddress shippingAddress = null;
		if(addressModel != null) {
		    shippingAddress = new ShippingAddress();
			shippingAddress.setCity(addressModel.getCity());
			shippingAddress.setLine1(addressModel.getAddressline1());
			shippingAddress.setPostalCode(addressModel.getPincode());
			shippingAddress.setState(addressModel.getState());
			shippingAddress.setCountryCode(addressModel.getCountry());
			shippingAddress.setRecipientName(userName);
		}
		
		Item item=null;
		ItemList itemList=new ItemList();
		List<Item> items=new ArrayList<Item>();
		
		for(CartItemModel cartItem:cartItemModelList) {
			item=new Item();
			item.setPrice(cartItem.getProduct().getPrice().toString());
			item.setQuantity(Integer.toString(cartItem.getQuantity()));
			item.setSku(cartItem.getSku());
			item.setCurrency(currency);
			total = total + (cartItem.getProduct().getPrice() *cartItem.getQuantity());
			items.add(item);
		}
		itemList.setShippingAddress(shippingAddress);
		
		itemList.setItems(items);
		
		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));
		
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);
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
