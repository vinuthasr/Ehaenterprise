package com.elephant.domain.payment;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.paypal.api.payments.Payment;

import springfox.documentation.spring.web.json.Json;
@Entity
@Table(name="PaymentDomain")
public class PaymentDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="payId")
	private int payId;
	@Column(name="paymentDetails")
	private String paymentDetails;
	@Column(name="status")
	private String status;
	@Lob
	@Column(name="payJson")
	private String payJson;
	@Column(name="txnId")
	private String txnId;

	
	
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
	public String getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayJson() {
		return payJson;
	}
	public void setPayJson(String payment) {
		this.payJson = payment;
	}
	
	

}
