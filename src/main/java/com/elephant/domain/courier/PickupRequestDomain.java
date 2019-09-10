package com.elephant.domain.courier;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pickupRequest")
public class PickupRequestDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3260670952304162808L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="courierOrderId")
	private long pickupRequestId;
	
	@Column(name="pickup_location")
	private String pickup_location;
	
	@Column(name="pickup_time")
	private String pickup_time;
	
	@Column(name="pickupDateDt")
	private Date pickupDateDt;
	
	private String pickup_date;
	
	@Column(name="expected_package_count")
	private String expected_package_count;

	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="modified_date")
	private Date modifiedDate;	
	
	@Column(name="pickup_id")
	private int pickup_id;
	
	@Column(name="incoming_center_name")
	private String incoming_center_name;
	
	public long getPickupRequestId() {
		return pickupRequestId;
	}

	public void setPickupRequestId(long pickupRequestId) {
		this.pickupRequestId = pickupRequestId;
	}

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

	public Date getPickupDateDt() {
		return pickupDateDt;
	}

	public void setPickupDateDt(Date pickupDateDt) {
		this.pickupDateDt = pickupDateDt;
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

}
