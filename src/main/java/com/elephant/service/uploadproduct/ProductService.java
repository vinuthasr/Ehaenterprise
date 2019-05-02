package com.elephant.service.uploadproduct;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

//import com.elephant.domain.uploadproduct.UploadProductDomain;
import com.elephant.model.uploadproduct.ProductModel;
import com.elephant.model.uploadproduct.ProductModel1;
import com.elephant.response.Response;


public interface ProductService {


//	public List<ProductModel> getProductByCatagory(String types,String colors, Float discount,Double length, double min,double max, String materialType, String fabricPurity, String pattern, String border, String borderType, String zariType, String blouse, String blouseColor, Double blouseLength)throws Exception;

	public Response addproduct(ProductModel model)throws Exception;

	public Response updateProduct(ProductModel updateproduct)throws Exception;

	public Response deleteproduct(String productId)throws Exception;
	
	public Response deleteProduct(String productId, boolean isActive)throws Exception;

	public Response exportExcel(MultipartFile file)throws Exception;

	public ProductModel getProductById(String productId)throws Exception;

	public List<ProductModel> getProductByDiscount(float discount)throws Exception;

	//List<UploadProductDomain> uploadFile(MultipartFile multipartFile) throws IOException;

	public List<ProductModel> getProductByColor(String colors) throws Exception;

	public List<ProductModel> getProductByOccassion(String occassion)throws Exception;

	public List<ProductModel> getProductByPrice(double price)throws Exception;

	public List<ProductModel> getProductByPriceRange(double minPrice,double maxPrice) throws Exception;

	public List<ProductModel> getProductByMaterialType(String materialType)throws Exception;

	public List<ProductModel> getProductByBorder(String border)throws Exception;

	public List<ProductModel> getProductByBorderType(String borderType)throws Exception;

	public List<ProductModel> getProductByZariType(String zariType)throws Exception;

	public List<ProductModel> getProductByBlouse(String blouse)throws Exception;

	public List<ProductModel> getProductByPattern(String pattern)throws Exception;

	public List<ProductModel> getProductByFabricPurity(String fabricPurity)throws Exception;

	public List<ProductModel> getProductByBlouseColor(String blouseColor)throws Exception;

	public List<ProductModel> getProductByBlouseLength(Double blouseLength)throws Exception;

	public List<ProductModel> getProductBySareeLength(Double length)throws Exception;

	public List<ProductModel> getProducts()throws Exception;

	public List<ProductModel> getProductByFilterDiscount(float discount)throws Exception;

	public List<ProductModel> getPriceSortingLowToHigh()throws Exception;

	public List<ProductModel> getPriceSortingHighToLow()throws Exception;

	public List<ProductModel> getProductByTypesSortingPriceLowToHigh(String types)throws Exception;

	public List<ProductModel> getProductByTypesSortingPriceHighToLow(String types)throws Exception;

	public List<ProductModel> getByNewProducts()throws Exception;

	public List<ProductModel> getProductBycategoryId(String categoryId)throws Exception;

	public List<ProductModel> getProductsByPriceRange(String categoryName, double minPrice, double maxPrice) throws Exception;

	public ByteArrayResource exportExcelHeaders(String fileName) throws IOException;

	public InputStream exportExcel() throws IOException;

	public List<ProductModel> getProductByCatagory1(ProductModel1 pm1);

    public boolean createExcelTemplate(ServletContext context,HttpServletRequest request, HttpServletResponse response);

}
