package com.elephant.model.courier;

import java.io.Serializable;
import java.util.Date;

public class CourierOrderResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6814109300689787244L;
	
	private String cash_pickups_count;
	private String package_count;
	private String upload_wbn;
	private String replacement_count;
	private String pickups_count;
	private String cash_pickups;
	private String cod_count;
	private String success;
	private String prepaid_count;
	private String cod_amount;
	private String rmk;
	private Packages packages[];
	private Date createdDate;
	private Date modifiedDate;
	
	
	public String getCash_pickups_count() {
		return cash_pickups_count;
	}
	public void setCash_pickups_count(String cash_pickups_count) {
		this.cash_pickups_count = cash_pickups_count;
	}
	public String getPackage_count() {
		return package_count;
	}
	public void setPackage_count(String package_count) {
		this.package_count = package_count;
	}
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	
	public String getUpload_wbn() {
		return upload_wbn;
	}
	public void setUpload_wbn(String upload_wbn) {
		this.upload_wbn = upload_wbn;
	}
	public String getReplacement_count() {
		return replacement_count;
	}
	public void setReplacement_count(String replacement_count) {
		this.replacement_count = replacement_count;
	}
	public String getPickups_count() {
		return pickups_count;
	}
	public void setPickups_count(String pickups_count) {
		this.pickups_count = pickups_count;
	}
	public String getCash_pickups() {
		return cash_pickups;
	}
	public void setCash_pickups(String cash_pickups) {
		this.cash_pickups = cash_pickups;
	}
	public String getCod_count() {
		return cod_count;
	}
	public void setCod_count(String cod_count) {
		this.cod_count = cod_count;
	}
	public String getPrepaid_count() {
		return prepaid_count;
	}
	public void setPrepaid_count(String prepaid_count) {
		this.prepaid_count = prepaid_count;
	}
	public String getCod_amount() {
		return cod_amount;
	}
	public void setCod_amount(String cod_amount) {
		this.cod_amount = cod_amount;
	}
	public Packages[] getPackages() {
		return packages;
	}
	public void setPackages(Packages[] packages) {
		this.packages = packages;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
