package com.elephant.dao.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.elephant.constant.StatusCode;
import com.elephant.dao.category.CategoryDaoImpl;
import com.elephant.domain.order.OrderDomain;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

	@Autowired
	OrderDaoRepository orderDaoRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);
	
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

	@Override
	public Integer getNoOfOrdersReceived() {
		int orderCount = 0;
		try {
			String sql = "SELECT count(*) FROM e_commerce.orders where order_status != 'Cancel-Order'";
		    orderCount = jdbcTemplate.queryForObject(sql,Integer.class);
		} catch (Exception e) {
			logger.error("Exception in getNoOfOrdersReceived", e);
		}
		return orderCount;
	}


	@Override
	public Double getTotalEarnings() {
		double totalEarnings = 0;
		try {
			String sql = "SELECT sum(order_price) FROM e_commerce.orders where order_status != 'Cancel-Order'";
			totalEarnings = jdbcTemplate.queryForObject(sql,Double.class);
		} catch (Exception e) {
			logger.error("Exception in getTotalEarnings", e);
		}
		return totalEarnings;
	}


	@Override
	public List<Map<String, Object>> getSalesReport() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> salesReportList = null;
		try {
			String sql = "select monthname(order_date) as Month, YEAR(order_date) as Year, count(*) as order_count from orders\r\n" + 
					"where order_date >= NOW() - INTERVAL 1 YEAR\r\n" + 
					"group by MONTH(order_date), YEAR(order_date) ";
			salesReportList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.error("Exception in getSalesReport", e);
		}
		return salesReportList;
	}
	
}
