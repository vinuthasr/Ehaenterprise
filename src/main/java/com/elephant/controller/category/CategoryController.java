package com.elephant.controller.category;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.dao.category.CategoryDao;
import com.elephant.domain.category.Category;
import com.elephant.model.category.CategoryModel;
import com.elephant.model.uploadproduct.ProductModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.category.CategoryService;
import com.elephant.utils.CommonUtils;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins= {"http://35.154.144.35:4200","http://localhost:4200","http://13.233.224.183:5200"})
public class CategoryController {
private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryDao categoryDao;
	
	//----------------------------Add Categories----------------------------------	
	//@PreAuthorize("hasRole('ROLE_ADMIN')" )
	@RequestMapping(value="/categories",method=RequestMethod.POST,produces="application/json" )
	public Response  addCategories(@Valid @RequestBody CategoryModel model,HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult) 
			throws Exception {
		return  (Response) categoryService.addCategories(model);
	}
	
	//----------------------------Get All Category----------------------------------
	@RequestMapping(value="/category/all", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String allCategories(HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("getCategories: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<CategoryModel>model=categoryService.allCategories();
		Response res = CommonUtils.getResponseObject("ALL Category Details");
	
		if (model.isEmpty()) {
			ErrorObject err = CommonUtils.getErrorResponse("Category Not Found", "Category Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
			res.setMessage("There are no products.");
		} else {
			res.setData(model);
		}
		logger.info("Category: Sent response");
		return CommonUtils.getJson(res);
	}
	
	//----------------------------Get Categories By Id----------------------------------
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCategoryById(@PathVariable("categoryId") String categoryId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getCategory: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		CategoryModel model = categoryService.getCategoryById(categoryId);
		Response res = CommonUtils.getResponseObject("Category Details");
		if (model == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Category Not Found", "Category Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(model);
		}
		logger.info("getCategory: Sent response");
		return CommonUtils.getJson(res);
	}
	
	//------------------------------Update Category--------------------------------------
	@RequestMapping(value = "/category", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateCategory(@RequestBody CategoryModel model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("categoryCongif: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("categoryCongif: Received request: " + CommonUtils.getJson(model));
		return categoryService.updateCategory(model);
	}
	
	//------------------------------Soft Delete Category--------------------------------------
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Response deleteCategory(@PathVariable("categoryId") String categoryId,@RequestParam(defaultValue="false") boolean isActive,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteCategory: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("categoryCongif: Received request: " + CommonUtils.getJson(categoryId));
		return categoryService.deleteCategory(categoryId,isActive);
	}
	
	//-----------------------------Delete------------------------------------------------
	@RequestMapping(value = "/category/data/{categoryId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Response deleteCategoryData(@PathVariable("categoryId") String categoryId,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteCategory: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("categoryCongif: Received request: " + CommonUtils.getJson(categoryId));
		return categoryService.deleteCategoryData(categoryId);
	}
	
	//------------------------------Get Products By CategoryName--------------------------------------
		@RequestMapping(value = "/Products/{categoryName}", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody String getProductsByCategoryName(@PathVariable("categoryName") String categoryName, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			logger.info("ProductsByCatagoryName: Received request: " + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
			logger.info("categoryCongif: Received request: " + CommonUtils.getJson(categoryName));
			//Response res=CommonUtils.getResponseObject("getProductsByCatagoryName");
			List<ProductModel> model= categoryService.getProductsByCategoryName(categoryName);
			Response res = CommonUtils.getResponseObject("List of product on particular category name");
			if (model==null) {
				ErrorObject err = CommonUtils.getErrorResponse("product Not Found", "product Not Found");
				res.setErrors(err);
				res.setStatus(StatusCode.ERROR.name());
				res.setMessage("product not found");
			} else {
				res.setData(model);
			}
			logger.info("getProductBycategoryName: Sent response");
			return CommonUtils.getJson(res);
		}
		
		//------------------------------Search Any--------------------------------------
		@RequestMapping(value = "/Product/{search}", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody String search( @RequestParam("any") String any,Model model, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			logger.info("ProductsByCatagoryNameInfo: Received request: " + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
			logger.info("categoryCongif: Received request: " + CommonUtils.getJson(any));
			//Response res=CommonUtils.getResponseObject("getProductsByCatagoryName");
			try {
			List<Category> domainModel= categoryService.search(any,model);
			Response res = CommonUtils.getResponseObject("List of product on particular category name");
			if (domainModel.isEmpty()) {
				ErrorObject err = CommonUtils.getErrorResponse("product Not Found", "product Not Found");
				res.setErrors(err);
				res.setStatus(StatusCode.ERROR.name());
				res.setMessage("product not found");
			} else {
				res.setData(domainModel);
			}
			
			logger.info("getProductBycategoryName: Sent response");
			return CommonUtils.getJson(res);
		}
			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@RequestMapping(value="/category/BtwDates", method=RequestMethod.GET, produces="application/json")
		public @ResponseBody String allCategoryBtw(HttpServletRequest request, HttpServletResponse response, Date endingDateAndTime)throws Exception{
			logger.info("getCategories: Received request: " + request.getRequestURL().toString()
					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
			CategoryModel model=categoryService.allCategoryBtw(endingDateAndTime);
			Response res = CommonUtils.getResponseObject("ALL Category Details");
		
			if (model==null) {
				ErrorObject err = CommonUtils.getErrorResponse("Category Not Found", "Category Not Found");
				res.setErrors(err);
				res.setStatus(StatusCode.ERROR.name());
				res.setMessage("There are no products.");
			} else {
				res.setData(model); 
			}
			logger.info("Category: Sent response");
			return CommonUtils.getJson(res);
		}
}
