package com.elephant.dao.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.elephant.constant.StatusCode;
import com.elephant.dao.category.CategoryDaoImpl;
import com.elephant.domain.courier.CourOrderDetDomain;
import com.elephant.domain.courier.PickupRequestDomain;
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
	
	@Autowired
	CourierOrderRepository courierOrderRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	PickupReqRepository pickupReqRepository;
	
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


	@Override
	public Response saveCourierDetails(CourOrderDetDomain courierOrderResponse) {
		Response response= CommonUtils.getResponseObject("add courier order details");
		try {
			courierOrderRepository.save(courierOrderResponse);
			response.setStatus(StatusCode.SUCCESS.name());
		}catch (Exception e) {
			//logger.error("Exception in addCustomer", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;

	}


	@Override
	public List<Map<String, Object>> getCourierOrderDetails(String status) {
		List<Map<String, Object>> courierOrderDetailsList = null;
		try {
			String sql = "SELECT courier.order_detail_id as Reference_No, courier.created_date as Order_Date,courier.status, \r\n" + 
					"courier.way_bill ,orderdet.product_name, orderdet.product_sku\r\n" + 
					"from courier_order_details courier, orderdetail orderdet where courier.status = ?\r\n" + 
					"and courier.order_detail_id = orderdet.orderdetail_id";
			courierOrderDetailsList =jdbcTemplate.queryForList(sql, new Object[] { status });
					//jdbcTemplate.query(sql, new Object[] { status }, new BeanPropertyRowMapper<String>(String.class));
			
		} catch (Exception e) {
			logger.error("Exception in getSalesReport", e);
		}
		return courierOrderDetailsList;
	}


	@Override
	public Response updateCourierOrderStatus(CourOrderDetDomain courOrderDetDomain) {
		Response response= CommonUtils.getResponseObject("update courier order details");
		try {
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("Status updated successfully");
		}catch (Exception e) {
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}


	@Override
	public CourOrderDetDomain getCourOrderDetById(String courierOrderId) {
		// TODO Auto-generated method stub
		return courierOrderRepository.findByCourierOrderId(courierOrderId);
	}


	@Override
	public String getWayBillNoByDetId(String orderDetailId) {
		String wayBillNo = null;
		try {
			String sql = "SELECT way_bill FROM e_commerce.courier_order_details where order_detail_id = ?";
			wayBillNo =(String) jdbcTemplate.queryForObject(sql, new Object[] { orderDetailId }, String.class);
					
			
		} catch (Exception e) {
			logger.error("Exception in getSalesReport", e);
		}
		return wayBillNo;
	}


	@Override
	public Response createPickupRequest(PickupRequestDomain pickupRequestDomain) {
		Response response=CommonUtils.getResponseObject("Create Courier Pick up request");
		try {
			if(pickupRequestDomain != null) {
				pickupReqRepository.save(pickupRequestDomain);
				response.setStatus(StatusCode.SUCCESS.name());
				response.setMessage("Creation of Pick up request is Successfull");
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return response;
	}


	@Override
	public List<PickupRequestDomain> getPickupReqDetails(Date fromDate, Date toDate) {
		List<PickupRequestDomain> pickupReqDomainList = null;
		try {
			pickupReqDomainList = new ArrayList<PickupRequestDomain>();
			pickupReqDomainList = pickupReqRepository.findAllByPickupDateDtBetween(fromDate,toDate);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return pickupReqDomainList;
	}
	
	
}
