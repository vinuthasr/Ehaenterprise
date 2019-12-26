package com.elephant.controller.categorymenu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.controller.category.CategoryController;
import com.elephant.model.categorymenu.CategoryMenuModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.categorymenu.CategoryMenuService;
import com.elephant.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins= {Constants.ADMIN_URL,Constants.CUSTOMER_URL,Constants.LOCALHOST_URL})
public class CategoryMenuController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	CategoryMenuService categoryMenuService;
	
	//----------------------------Add Main Categories----------------------------------	
	@RequestMapping(value="/categorymenus/save",method=RequestMethod.POST,produces="application/json" )
	public Response  addCategoryMenus(@Valid @RequestBody CategoryMenuModel categoryMenuModel,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("addCategoryMenus: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		Response res = CommonUtils.getResponseObject("Save Category Menu Details");
		res =  categoryMenuService.addCategoryMenus(categoryMenuModel);
		logger.info("addCategoryMenus: Sent response");
		return res;
	}
	
	@RequestMapping(value = "/categorymenus/update", method = RequestMethod.PUT, produces = "application/json")
	public Response updateCategoryMenu(@RequestBody CategoryMenuModel categoryMenuModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateCategoryMenu: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateCategoryMenu: Received request: " + CommonUtils.getJson(categoryMenuModel));
		return categoryMenuService.updateCategoryMenu(categoryMenuModel);
	}

	
	//----------------------------Get All Category Menu----------------------------------
	@RequestMapping(value="/categorymenus/all", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String allCategorymenus(HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("allCategorymenus: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<CategoryMenuModel> categoryMenuModelList=categoryMenuService.allCategoryMenus();
		Response res = CommonUtils.getResponseObject("ALL Category Menu Details");
	
		if (categoryMenuModelList.isEmpty()) {
			ErrorObject err = CommonUtils.getErrorResponse("Category Menus Not Found", "Category Menus Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
			res.setMessage("No records found.");
		} else {
			res.setData(categoryMenuModelList);
		}
		logger.info("allCategorymenus: Sent response");
		return CommonUtils.getJson(res);
	}
	
	
	
	
}
