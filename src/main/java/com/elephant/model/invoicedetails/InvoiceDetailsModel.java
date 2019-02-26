package com.elephant.model.invoicedetails;

import com.elephant.model.invoice.InvoiceModel;

public class InvoiceDetailsModel {

	private long invoiceDetailId;
	
	private String productSku;
	
	

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	private String productName;
	
	private int productQuantity;
	
	private double productAmount;
	
    private InvoiceModel invoiceModel;
	
	public long getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(long invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}

	public InvoiceModel getInvoiceModel() {
		return invoiceModel;
	}

	public void setInvoiceModel(InvoiceModel invoiceModel) {
		this.invoiceModel = invoiceModel;
	}

	
    
	
	

}
