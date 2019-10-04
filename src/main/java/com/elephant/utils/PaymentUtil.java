package com.elephant.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.elephant.constant.Constants;
import com.elephant.model.payment.PaymentDetail;

public class PaymentUtil {
	private static final String paymentKey = "ZrAG53ds";

    private static final String paymentSalt = "Dc2AaaA81p";

    private static final String sUrl = Constants.BACKEND_URL+"/v1/paymentresponse";

    private static final String fUrl = Constants.BACKEND_URL+"/v1/paymentresponse";
    
    public static PaymentDetail populatePaymentDetail(PaymentDetail paymentDetail){
        String hashString = "";
        Random rand = new Random();
        String randomId = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
        String txnId = "Dev" + hashCal("SHA-256", randomId).substring(0, 12);
        paymentDetail.setTxnId(txnId);
        String hash = "";
        //String otherPostParamSeq = "phone|surl|furl|lastname|curl|address1|address2|city|state|country|zipcode|pg";
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|||||||||||";
        hashString = hashSequence.concat(paymentSalt);
        hashString = hashString.replace("key", paymentKey);
        hashString = hashString.replace("txnid", txnId);
        hashString = hashString.replace("amount", paymentDetail.getAmount());
        if(null != paymentDetail.getProductInfo()) {
        	 hashString = hashString.replace("productinfo", paymentDetail.getProductInfo());
        } else {
        	 hashString = hashString.replace("productinfo", "product info");
        }
       
        hashString = hashString.replace("firstname", paymentDetail.getFirstName());
        hashString = hashString.replace("email", paymentDetail.getEmail());

        hash = hashCal("SHA-512", hashString);
        paymentDetail.setHash(hash);
        paymentDetail.setfUrl(fUrl);
        paymentDetail.setsUrl(sUrl);
        paymentDetail.setKey(paymentKey);
        System.out.println("hash: " +hash);
        System.out.println("txnid: " +txnId);
        return paymentDetail;
    }
    
    public static String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

}
