package com.elephant.service.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephant.config.PaypalPaymentIntent;
import com.elephant.config.PaypalPaymentMethod;
import com.elephant.constant.PaymentStatus;
import com.elephant.dao.address.AddressDao;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.payment.PUMRepo;
import com.elephant.dao.payment.PaymentDao;
import com.elephant.domain.address.AddressDomain;
import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.payment.PUMPaymentDomain;
import com.elephant.domain.payment.PaymentDomain;
import com.elephant.model.payment.PaymentCallback;
import com.elephant.model.payment.PaymentDetail;
import com.elephant.utils.PaymentUtil;
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
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AddressDao addressDao;
	
	@Autowired
	PUMRepo pumRepository;
	
	public Payment createPayment(
			long addressId,
			String email, 
			String currency, 
			PaypalPaymentMethod method, 
			PaypalPaymentIntent intent, 
			String description, 
			String cancelUrl, 
			String successUrl) throws Exception{
		double total =0.0;
		//amount.setTotal(total);
		CustomerDomain customerDomain=customerRepository.findByEmail(email);
		
		AddressDomain addressDomain=addressDao.getAddressById(addressId);
		
		List<CartItemDomain> cartItemDomainList=customerDomain.getCartItemDomain();
		
		ShippingAddress shippingAddress = null;
		if(addressDomain != null) {
		    shippingAddress = new ShippingAddress();
			shippingAddress.setCity(addressDomain.getCity());
			shippingAddress.setLine1(addressDomain.getAddressline1());
			shippingAddress.setPostalCode(addressDomain.getPincode());
			shippingAddress.setState(addressDomain.getState());
			shippingAddress.setCountryCode("IN");
			shippingAddress.setRecipientName(customerDomain.getCustomerName());
		}
		
		Item item=null;
		ItemList itemList=new ItemList();
		List<Item> items=new ArrayList<Item>();
		
		for(CartItemDomain cartItem:cartItemDomainList) {
			item=new Item();
			item.setPrice(cartItem.getProduct().getPrice().toString());
			item.setQuantity(Integer.toString(cartItem.getQuantity()));
			item.setSku(cartItem.getProduct().getSku());
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
	
	
	/* PUM */
	 @SuppressWarnings("static-access")
	public PaymentDetail proceedPayment(String email) {
		 
		 CustomerDomain customerDomain=customerRepository.findByEmail(email);
		 List<CartItemDomain> cartItemDomainList=customerDomain.getCartItemDomain();
		 double total =0.0;
		 String productInfo = null;
		 for(CartItemDomain cartItem:cartItemDomainList) {
				total = total + (cartItem.getProduct().getPrice() *cartItem.getQuantity());
				productInfo = cartItem.getProduct().getProductName();
		 }
		 
		 PaymentDetail paymentDetail= new PaymentDetail();
		 paymentDetail.setAmount(String.valueOf(total));
		 paymentDetail.setEmail(email);
		 paymentDetail.setFirstName(customerDomain.getCustomerName());
		 paymentDetail.setPhone(String.valueOf(customerDomain.getMobileNumber()));
		 paymentDetail.setProductInfo(productInfo);
		 
	     PaymentUtil paymentUtil = new PaymentUtil();
	     paymentDetail = paymentUtil.populatePaymentDetail(paymentDetail);
	     savePaymentDetail(paymentDetail);
	     return paymentDetail;
	 }
	 
	 private void savePaymentDetail(PaymentDetail paymentDetail) {
		 PUMPaymentDomain payment = new PUMPaymentDomain();
	        payment.setAmount(Double.parseDouble(paymentDetail.getAmount()));
	        payment.setEmail(paymentDetail.getEmail());
	        payment.setName(paymentDetail.getFirstName());
	        payment.setPaymentDate(new Date());
	        payment.setPaymentStatus(PaymentStatus.Pending);
	        payment.setPhone(paymentDetail.getPhone());
	        payment.setProductInfo(paymentDetail.getProductInfo());
	        payment.setTxnId(paymentDetail.getTxnId());
	        pumRepository.save(payment);
	  }
	 
    public PUMPaymentDomain payuCallback(PaymentCallback paymentResponse) {
        PUMPaymentDomain payment = pumRepository.findByTxnId(paymentResponse.getTxnid());
        if(payment != null) {
            //TODO validate the hash
            PaymentStatus paymentStatus = null;
            if(paymentResponse.getStatus().equals("failure")){
                paymentStatus = PaymentStatus.Failed;
            }else if(paymentResponse.getStatus().equals("success")) {
                paymentStatus = PaymentStatus.Success;
            }
            payment.setPaymentStatus(paymentStatus);
            payment.setMihpayId(paymentResponse.getMihpayid());
            payment.setMode(paymentResponse.getMode());
            pumRepository.save(payment);
        }
        return payment;
    }

}
