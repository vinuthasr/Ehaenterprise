package com.elephant.model.courier;

import java.io.Serializable;
import java.util.Date;

public class PickupReqModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5932065591492187012L;
	private String pickup_location;
	private String pickup_time;
	private String pickup_date;
	private Date pickupDateDt;
	private String expected_package_count;
	private Date createdDate;
	private Date modifiedDate;	
	private int pickup_id;
	private String incoming_center_name;
	
	public String getPickup_location() {
		return pickup_location;
	}
	public void setPickup_location(String pickup_location) {
		this.pickup_location = pickup_location;
	}
	public String getPickup_time() {
		return pickup_time;
	}
	public void setPickup_time(String pickup_time) {
		this.pickup_time = pickup_time;
	}
	

	public String getPickup_date() {
		return pickup_date;
	}
	public void setPickup_date(String pickup_date) {
		this.pickup_date = pickup_date;
	}
	public String getExpected_package_count() {
		return expected_package_count;
	}
	public void setExpected_package_count(String expected_package_count) {
		this.expected_package_count = expected_package_count;
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
	public int getPickup_id() {
		return pickup_id;
	}
	public void setPickup_id(int pickup_id) {
		this.pickup_id = pickup_id;
	}
	public String getIncoming_center_name() {
		return incoming_center_name;
	}
	public void setIncoming_center_name(String incoming_center_name) {
		this.incoming_center_name = incoming_center_name;
	}
	public Date getPickupDateDt() {
		return pickupDateDt;
	}
	public void setPickupDateDt(Date pickupDateDt) {
		this.pickupDateDt = pickupDateDt;
	}
	
}
