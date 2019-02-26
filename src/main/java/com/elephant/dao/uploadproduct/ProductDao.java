package com.elephant.dao.uploadproduct;

import java.util.List;
import java.util.Optional;

import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.model.category.CategoryModel;
import com.elephant.model.uploadproduct.ProductModel;
import com.elephant.model.uploadproduct.ProductModel1;
import com.elephant.response.Response;


public interface ProductDao {




//	public List<ProductDomain> getProductByCatagory(String categoryName, String colors, Float discount, Double length, double min,double max, String materialType, String fabricPurity, String pattern, String border, String borderType, String zariType, String blouse, String blouseColor, Double blouseLength)throws Exception;



	public Response addproduct(ProductDomain update)throws Exception;



	public Response updateProduct(ProductDomain update)throws Exception;



	public Response deleteproduct(String productId)throws Exception;


	public Response deleteProduct(String productId, boolean isActive)throws Exception;

	public Response save(ProductDomain update)throws Exception;



	public ProductDomain getProductById(String productId)throws Exception;


	public List<ProductDomain> getProductByDiscount(float discount)throws Exception;



	public List<ProductDomain> uploadFile(List<ProductDomain> productuploadList)throws Exception;



	public List<ProductDomain> getProductByColor(String colors)throws Exception;



	public List<ProductDomain> getProductByOccassion(String occassion)throws Exception;



	public List<ProductDomain> getProductByPrice(double price)throws Exception;



	public List<ProductDomain> getProductByPriceRange(double minPrice, double maxPrice)throws Exception;



	public List<ProductDomain> getProductByMaterialType(String materialType)throws Exception;



	public List<ProductDomain> getProductByBorder(String border)throws Exception;



	public List<ProductDomain> getProductByBorderType(String borderType)throws Exception;



	public List<ProductDomain> getProductByZariType(String zariType)throws Exception;



	public List<ProductDomain> getProductByBlouse(String blouse) throws Exception;



	public List<ProductDomain> getProductByPattern(String pattern)throws Exception;



	public List<ProductDomain> getProductByFabricPurity(String fabricPurity)throws Exception;



	public List<ProductDomain> getProductByBlouseColor(String blouseColor)throws Exception;



	public List<ProductDomain> getProductByBlouseLength(Double blouseLength)throws Exception;



	public List<ProductDomain> getProductBySareeLength(Double length)throws Exception;



	public List<ProductDomain> getProducts()throws Exception;



	public List<ProductDomain> getProductByFilterDiscount(float discount)throws Exception;



	public List<ProductDomain> getPriceSortingLowToHigh()throws Exception;



	public List<ProductDomain> getPriceSortingHighToLow()throws Exception;



	public List<ProductDomain> getProductByTypesSortingPriceLowToHigh(String types)throws Exception;



	public List<ProductDomain> getProductByTypesSortingPriceHighToLow(String types)throws Exception;



	public List<ProductDomain> getByNewProducts()throws Exception;






	public List<ProductDomain> getProductBycategoryId(String categoryId)throws Exception;



	public Response deleteproductByCategoryId(String categoryId, boolean isActive)throws Exception;



	public List<ProductDomain> getProductsByPriceRange(String categoryName, double minPrice, double maxPrice)throws Exception;



	public List<ProductDomain> getProductByCatagory1(ProductModel1 pm1);












	








	
	
}
