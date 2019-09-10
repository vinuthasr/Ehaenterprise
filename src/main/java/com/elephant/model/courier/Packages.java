package com.elephant.model.courier;

import java.io.Serializable;

public class Packages implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1589531241888373950L;
	
	private String status;
	private String client;
	private String waybill;
	private String[] remarks;
	private String sort_code;
	private String cod_amount;
	private String payment;
	private String serviceable;
	private String refnum;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getWaybill() {
		return waybill;
	}
	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	
	public String getSort_code() {
		return sort_code;
	}
	public void setSort_code(String sort_code) {
		this.sort_code = sort_code;
	}
	public String getCod_amount() {
		return cod_amount;
	}
	public void setCod_amount(String cod_amount) {
		this.cod_amount = cod_amount;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getServiceable() {
		return serviceable;
	}
	public void setServiceable(String serviceable) {
		this.serviceable = serviceable;
	}
	public String getRefnum() {
		return refnum;
	}
	public void setRefnum(String refnum) {
		this.refnum = refnum;
	}
	
}
