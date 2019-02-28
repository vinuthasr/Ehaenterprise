package com.elephant.controller.cartitem;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.dao.customer.CustomerRepository;
import com.elephant.model.cartitem.CartItemModel;
import com.elephant.model.customer.CustomerModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.cartitem.CartItemsService;
import com.elephant.utils.CommonUtils;

@RestController
@RequestMapping(value="/cartitem")
@CrossOrigin(origins= {"https://eha-admin-app.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com"})
public class CartItemController {

	@Autowired
	CartItemsService cartItemsService;
	
	@Autowired
	CustomerRepository customerDaoRepository;
	
	@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/save" , method = RequestMethod.POST,produces="application/json")
	public Response saveItem(@RequestBody List<CartItemModel> cartItemModel,  Principal currentUser) {
		
		
		return cartItemsService.saveItem(cartItemModel,currentUser);
		
	}
	
	@RequestMapping(value = "/edit/{cartItemId}", method = RequestMethod.PUT,produces="application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Response editItem(@RequestParam (value = "quantity") int quantity,
                         @PathVariable (value = "cartItemId") long cartItemId){
        
		
		return cartItemsService.editItem(quantity, cartItemId);
		
		
    }
	
	 @RequestMapping(value = "/remove/{cartItemId}", method = RequestMethod.DELETE,produces="application/json")
	    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	    public Response removeItem(@PathVariable (value = "cartItemId") long cartItemId){
		 return cartItemsService.removeItem(cartItemId);
	    }
	
	 @RequestMapping(value="/getAllCartItem",method = RequestMethod.PUT,produces="application/json")
	 public String getAllCartItem(){
		 Response res=CommonUtils.getResponseObject("Get AlL CartItems");
			List<CartItemModel> cartItemModel=cartItemsService.getAllCartItem();
			 if(cartItemModel.isEmpty()) {
				 ErrorObject err=CommonUtils.getErrorResponse("no cartitems", "null items");
				 res.setErrors(err);
				 res.setStatus(StatusCode.ERROR.name());
			 }
			 else
			 {
				 res.setData(cartItemModel);
			 }
			 
			 return CommonUtils.getJson(res);
	 }
	 
	 @RequestMapping(value="/getCartItemsByCustomer/{email}",method = RequestMethod.GET,produces="application/json")
	 public String getCartItemsByCustomer(@RequestParam(value="email") String email){
		Response res=CommonUtils.getResponseObject("Get AlL CartItems by Customer");
		List<CartItemModel> cartItemModel=cartItemsService.getCartItemsByCustomer(email);
		 if(cartItemModel.isEmpty()) {
			 ErrorObject err=CommonUtils.getErrorResponse("no cartitems", "null items");
			 res.setErrors(err);
			 res.setStatus(StatusCode.ERROR.name());
		 }
		 else
		 {
			 res.setData(cartItemModel);
			 res.setStatus(StatusCode.SUCCESS.name());
		 }
		 
		 return CommonUtils.getJson(res);
		 
	 }
}
