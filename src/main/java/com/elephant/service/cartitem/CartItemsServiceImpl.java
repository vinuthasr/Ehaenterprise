package com.elephant.service.cartitem;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elephant.constant.StatusCode;
import com.elephant.dao.cartitem.CartItemDaoRepository;
import com.elephant.dao.customer.CustomerDao;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.uploadproduct.ProductRepository;
import com.elephant.domain.cartitem.CartItemDomain;
import com.elephant.domain.customer.CustomerDomain;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.mapper.cartitem.CartItemMapper;
//import com.elephant.model.cart.CartModel;
import com.elephant.model.cartitem.CartItemModel;
import com.elephant.response.Response;
import com.elephant.service.customer.CustomerService;
import com.elephant.utils.CommonUtils;



@Service
public class CartItemsServiceImpl implements CartItemsService{
	
	
	private static final Logger logger = LoggerFactory.getLogger(CartItemsServiceImpl.class);

	
	
	@Autowired
	CartItemMapper cartItemMapper;
	
	@Autowired
	CartItemDaoRepository cartItemsDaoRepository;
	
	@Autowired
	CustomerRepository  customerDaoRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartItemDaoRepository cartItemDaoRepository;
	
	/*@Autowired
	CartDaoRepository cartDaoRepository;*/
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerDao customerDao;
	
	
	//@SuppressWarnings("unchecked")
	
	@Override
	public Response saveItem(List<CartItemModel> cartItemModelList, Principal cu) {
	
      Response response=CommonUtils.getResponseObject("Save Cart Item");
		
		try {
				CartItemDomain cartItemDomain = null;
				for(CartItemModel cartItemModel:cartItemModelList) {
					cartItemDomain=cartItemsDaoRepository.getOne(cartItemModel.getCartItemId());
    				if(cartItemModel.getQuantity() <= cartItemDomain.getProduct().getInStock()) {
	    				cartItemDomain.setQuantity(cartItemModel.getQuantity());
	    				cartItemsDaoRepository.save(cartItemDomain);
	    				
	    				response.setStatus(StatusCode.SUCCESS.name());
	    				response.setMessage("Quantity updated successfully");
    				}else {
    					logger.info("quantity is more for:"+cartItemModel.getSku());
    					response.setStatus(StatusCode.ERROR.name());
	    		        response.setMessage("quantity is more for: " +cartItemModel.getSku());
    				    					
    				}
				}
		}catch(Exception ex) {
			System.out.println("Exception in save cartItem"+ex);
		}
		
		return response;
	}

	@Override
	public  Response editItem(int quantity, long cartItemId) {
		
		Response response=CommonUtils.getResponseObject("Edit item");
		try {
		CartItemDomain cartItemDomain = cartItemsDaoRepository.getOne(cartItemId); 
        if(quantity <= cartItemDomain.getProduct().getInStock() )	
        	cartItemDomain.setQuantity(quantity); 
        else
        {
        	response.setMessage("Not so much quantity in stock.");
        	return response;
        	//throw new IllegalArgumentException("Not so much quantity in stock.");
        }
       /* int totalprice=cartItemDomain.getProduct().getPrice()-((cartItemDomain.getProduct().getPrice()*cartItemDomain.getProduct().getDiscount()/100));
        cartItemDomain.setTotalprice(totalprice*cartItemDomain.getQuantity());*/
       
        cartItemDaoRepository.save(cartItemDomain);
        response.setStatus(StatusCode.SUCCESS.name());
        response.setMessage1("Edit item Successfull");
        return response;
		}
		catch(Exception ex) {
			System.out.println("Exception in editItem"+ex);
		}
		return null;
	}
	
	@Override
	public Response removeItem(long cartItemId) {
		Response response = CommonUtils.getResponseObject("Remove Item");
		try {
		cartItemDaoRepository.deleteById(cartItemId);
		response.setStatus(StatusCode.SUCCESS.name());
		return response;
		}catch(Exception ex){
			response.setStatus(StatusCode.ERROR.name());
			System.out.println("Exception in removing item"+ex);
		}
		return response;
	}

	@Override
	public List<CartItemModel> getAllCartItem() {
		try {
			List<CartItemDomain> cartItemDomainList = cartItemDaoRepository.findAll();
			return cartItemMapper.entityList(cartItemDomainList);
		} catch (Exception ex) {
			System.out.println("Exception getCustomers:");
		}
		return null;
	}

	@Override
	public List<CartItemModel> getCartItemsByCustomer(String email) {
		try {
		CustomerDomain customerDomain=customerDaoRepository.findByEmail(email);
		List<CartItemDomain> cartItemDomainList=customerDomain.getCartItemDomain();
		return cartItemMapper.entityList(cartItemDomainList) ;
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return null;
	}

	@Override
	public Response addItemToCart(CartItemModel cartItemModel, Principal currentUser) {
		Response response = CommonUtils.getResponseObject("Add Item to Cart");
		boolean isExist = false;
		try {
			if(cartItemModel != null) {
				ProductDomain productDomain=productRepository.findBySku(cartItemModel.getSku());
				CustomerDomain customerDomain = customerDaoRepository.findByEmail(currentUser.getName());
				if(productDomain != null) {
					List<CartItemDomain> listCartItemDomain=customerDomain.getCartItemDomain();
					for(CartItemDomain cartItemDomain:listCartItemDomain) {
						if(cartItemDomain.getProduct().getSku().equalsIgnoreCase(productDomain.getSku())) {
							response.setStatus(StatusCode.ERROR.name());
		    				response.setMessage("Product "+productDomain.getSku()+" is already added in the cart ");
		    				isExist = true;
		    				break;
						}
					}
					
					if(!isExist) {
						CartItemDomain cartItemDomain = null;
						if(productDomain.getInStock() > 1) {
							cartItemDomain=new CartItemDomain();
							cartItemDomain.setQuantity(cartItemModel.getQuantity());
		    				cartItemDomain.setProduct(productDomain);
		    				cartItemDomain.setCustomerDomain(customerDomain);
		    				cartItemsDaoRepository.save(cartItemDomain);
		    				
		    				response.setStatus(StatusCode.SUCCESS.name());
		    				response.setMessage("Cart Item is success");
						} else {
							response.setStatus(StatusCode.ERROR.name());
		    				response.setMessage("Quantity is more for product "+productDomain.getSku());
						}
					}
				} else  {
					response.setStatus(StatusCode.ERROR.name());
    				response.setMessage("Product does not exist");
				}
					
				
			}	
		}catch(Exception ex) {
			throw new RuntimeException("Exception "+ex);
		}
		return response;
	}
	
}


/*------------------------------------------------------------------Original Code ------------------------------------------------------*/
/*
@Override
public CartItemModel saveItem(long productId, int quantity, String email) {

	CustomerDomain customerDomain=customerDaoRepository.findByEmail(email);
	CartDomain cartDomain=cartDaoRepository.findByCartId(customerDomain.getCartDomain().getCartId());
	
	if(customerDomain != null){
		if (quantity == 0){
			throw new IllegalArgumentException("Quantity = null");
		}
	}

        List<CartItemDomain> cartItemDomain = cartDomain.getCartItem();
        ProductDomain product=productRepository.getOne(productId);
        
         If the Cart Items contains same products
        for(int i = 0; i<cartItemDomain.size(); i++){
            if (product.getProductId()==cartItemDomain.get(i).getProduct().getProductId()){
            	CartItemDomain cartItemDomain1= cartItemDomain.get(i);
                if(cartItemDomain1.getQuantity()+quantity <= product.getInStock()) {
                	cartItemDomain1.setQuantity(cartItemDomain1.getQuantity() + quantity);
                }else {
                	//cartItemDomain1.setQuantity(product.getInStock());
                    throw new IllegalArgumentException("Not so much quantity in stock.");
                }
                cartItemDomain1.setTotalprice((product.getPrice()-((product.getPrice()*product.getDiscount()/100)))*cartItemDomain1.getQuantity());
                cartItemDaoRepository.save(cartItemDomain1);
                CartItemModel cartItemModel=new CartItemModel();
                BeanUtils.copyProperties(cartItemDomain1, cartItemModel);
            
                return cartItemModel;
            }
        }
      
        CartItemDomain cartItem = new CartItemDomain();
        cartItem.setProduct(product);
        if(cartItem.getQuantity()+quantity <= product.getInStock() )	
        	cartItem.setQuantity(cartItem.getQuantity()+quantity);
        else
        	throw new IllegalArgumentException("Not so much quantity in stock.");
        
        int totalprice=product.getPrice()-((product.getPrice()*product.getDiscount()/100));
        cartItem.setTotalprice(totalprice*cartItem.getQuantity());
        cartItem.setCartDomain(cartDomain);
        cartItemDaoRepository.save(cartItem);
        
        CartItemModel cartItemModel=new CartItemModel();
        BeanUtils.copyProperties(cartItem, cartItemModel);
        return cartItemModel;
}

@Override
	public  CartItemModel editItem(int quantity, long cartItemId) {
		
		try {
		CartItemDomain cartItemDomain = cartItemsDaoRepository.getOne(cartItemId); 
        if(quantity <= cartItemDomain.getProduct().getInStock() )	
        	cartItemDomain.setQuantity(quantity); 
        else
        	throw new IllegalArgumentException("Not so much quantity in stock.");
        
        int totalprice=cartItemDomain.getProduct().getPrice()-((cartItemDomain.getProduct().getPrice()*cartItemDomain.getProduct().getDiscount()/100));
        cartItemDomain.setTotalprice(totalprice*cartItemDomain.getQuantity());
        cartItemDaoRepository.save(cartItemDomain);
        CartItemModel cartItemModel=new CartItemModel();
        BeanUtils.copyProperties(cartItemDomain, cartItemModel);
        return cartItemModel;
		}
		catch(Exception ex) {
			System.out.println("Exception in editItem"+ex);
		}
		return null;
	}
	
	@Override
	public Response removeItem(long cartItemId) {
		Response response = CommonUtils.getResponseObject("Remove Item");
		try {
		cartItemDaoRepository.deleteById(cartItemId);
		response.setStatus(StatusCode.SUCCESS.name());
		return response;
		}catch(Exception ex){
			response.setStatus(StatusCode.ERROR.name());
			System.out.println("Exception in removing item"+ex);
		}
		return response;
	}

	@Override
	public List<CartItemModel> getAllCartItem() {
		try {
			List<CartItemDomain> cartItemDomain = cartItemDaoRepository.findAll();
			return cartItemMapper.entityList(cartItemDomain);
		} catch (Exception ex) {
			System.out.println("Exception getCustomers:");
		}
		return null;
	}

	@Override
	public List<CartItemModel> getCartItemsByCustomer(String email) {
		try {
		CustomerDomain customerDomain=customerDaoRepository.findByEmail(email);
		List<CartItemDomain> cartItemDomain=customerDomain.getCartDomain().getCartItem();
		return cartItemMapper.entityList(cartItemDomain) ;
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return null;
	}

*/
/*--------------------------------------------------------------------------------------------------------------------------------------------------*/