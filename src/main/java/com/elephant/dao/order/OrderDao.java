package com.elephant.dao.order;

import java.util.Date;
import java.util.List;

import com.elephant.domain.order.OrderDomain;
import com.elephant.response.Response;

public interface OrderDao {

	public Response create(OrderDomain orderDomain);
	public List<OrderDomain> getAllOrders();
	//public List<OrderDomain> totalOrdersPerday();
	public Response cancelOrder();
	public List<OrderDomain> getOrdersByDate(Date orderDate);
	public List<OrderDomain> totalOrdersFromDateToParticularDate(Date fromDate, Date toDate);
	
}
