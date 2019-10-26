package com.elephant.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.elephant.domain.courier.CourOrderDetDomain;
import com.elephant.domain.courier.PickupRequestDomain;
import com.elephant.domain.order.OrderDomain;
import com.elephant.response.Response;

public interface OrderDao {

	public Response create(OrderDomain orderDomain);
	public List<OrderDomain> getAllOrders();
	//public List<OrderDomain> totalOrdersPerday();
	public Response cancelOrder();
	public List<OrderDomain> getOrdersByDate(Date orderDate);
	public List<OrderDomain> totalOrdersFromDateToParticularDate(Date fromDate, Date toDate);
	public Integer getNoOfOrdersReceived(); 
	public Double getTotalEarnings();
	public List<Map<String, Object>> getSalesReport();
	public Response saveCourierDetails(CourOrderDetDomain courierOrderResponse);
	public List<Map<String, Object>> getCourierOrderDetails(String status);
	public Response updateCourierOrderStatus(CourOrderDetDomain courOrderDetDomain);
	public CourOrderDetDomain getCourOrderDetById(String courierOrderId);
	public String getWayBillNoByDetId(String orderDetailId);
	public Response createPickupRequest(PickupRequestDomain pickupRequestDomain);
	public List<PickupRequestDomain> getPickupReqDetails(Date fromDate,Date toDate);
	public List<Map<String, Object>> getOrderDetails(String fromDate, String toDate);
}
