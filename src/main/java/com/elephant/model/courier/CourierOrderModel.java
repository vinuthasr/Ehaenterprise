package com.elephant.model.courier;

import java.io.Serializable;

public class CourierOrderModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 699664339573062765L;
	
	private Shipments shipments[];
	private PickupLocation pickup_location;
	
	public Shipments[] getShipments() {
		return shipments;
	}
	public void setShipments(Shipments[] shipments) {
		this.shipments = shipments;
	}
	public PickupLocation getPickup_location() {
		return pickup_location;
	}
	public void setPickup_location(PickupLocation pickup_location) {
		this.pickup_location = pickup_location;
	}
	
}
