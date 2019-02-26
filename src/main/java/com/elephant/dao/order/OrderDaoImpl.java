package com.elephant.dao.order;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.elephant.constant.StatusCode;
import com.elephant.domain.order.OrderDomain;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

	@Autowired
	OrderDaoRepository orderDaoRepository;
	
	@Override
	public Response create(OrderDomain orderDomain) {
		
		Response response= CommonUtils.getResponseObject("add customer order");
		try {
			orderDaoRepository.save(orderDomain);
			response.setStatus(StatusCode.SUCCESS.name());
		}catch (Exception e) {
			//logger.error("Exception in addCustomer", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;

	}

	
	public List<OrderDomain> getAllOrders(){
		return orderDaoRepository.findAll();
	}


	/*@Override
	public List<OrderDomain> totalOrdersPerday() {
		// TODO Auto-generated method stub
		return null;
	}
*/

	@Override
	public Response cancelOrder() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<OrderDomain> getOrdersByDate(Date orderDate) {
		return orderDaoRepository.findAllByOrderDate(orderDate);
	}


	@Override
	public List<OrderDomain> totalOrdersFromDateToParticularDate(Date fromDate, Date toDate) {
		
		return orderDaoRepository.findAllByOrderDateBetween(fromDate, toDate);
	}


	
	
	
	
	
}
