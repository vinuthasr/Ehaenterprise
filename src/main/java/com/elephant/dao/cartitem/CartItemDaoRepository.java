package com.elephant.dao.cartitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;

public interface CartItemDaoRepository extends JpaRepository<CartItemDomain, Long>{

	//public List<CartItemDomain> findCartItemsByCustomer(CustomerDomain customerDomain);
//	@Repository
//	@Transactional
//	public class CartDaoImpl implements CartDao{
//
//		@PersistenceContext
//		EntityManager entityManager;
//		
//		@Autowired
//		CartDaoRepository cartDaoRepository;
//		
//		@Override
//		public Response createCart(CartDomain cartDomain) {
//			Response response=CommonUtils.getResponseObject("Creating cart");
//			try {
//			entityManager.persist(cartDomain);
//			response.setStatus(StatusCode.SUCCESS.name());
//			response.getStatus();
//			}
//			catch(Exception ex) {
//				System.out.println("exception in creating cart"+ex);
//				response.setStatus(StatusCode.ERROR.name());
//				response.setErrors(ex.getMessage());
//			}
//			return response;
		//}

}
