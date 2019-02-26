package com.elephant.domain.invoicedetails;

import java.io.Serializable;

import javax.persistence.*;

import com.elephant.domain.invoice.InvoiceDomain;

@Entity
@Table(name="invoicedetail")
public class InvoiceDetailsDomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5169460487140765271L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long invoiceDetailId;
	

	@Column(name="productSku")
	private String productSku;	
	
	

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	@Column(name="productName")
	private String productName;
	
	@Column(name="productQuantity")
	private int productQuantity;
	
	@Column(name="productAmount")
	private double productAmount;
	
	@ManyToOne
	@JoinColumn(name="invoiceId")
    private InvoiceDomain invoiceDomain;
	
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

	public InvoiceDomain getInvoiceDomain() {
		return invoiceDomain;
	}

	public void setInvoiceDomain(InvoiceDomain invoiceDomain) {
		this.invoiceDomain = invoiceDomain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
