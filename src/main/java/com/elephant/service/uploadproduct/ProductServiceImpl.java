package com.elephant.service.uploadproduct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.elephant.constant.FileType;
import com.elephant.constant.StatusCode;
import com.elephant.dao.category.CategoryRepository;
import com.elephant.dao.subimage.SubImageDaoRepository;
import com.elephant.dao.uploadproduct.ProductDao;
import com.elephant.dao.uploadproduct.ProductRepository;
import com.elephant.domain.category.Category;
import com.elephant.domain.subimages.SubImageDomain;
import com.elephant.domain.uploadproduct.BulkProduct;
import com.elephant.domain.uploadproduct.ProductDomain;
import com.elephant.mapper.category.CategoryMapper;
import com.elephant.mapper.uploadproduct.ProductMapper;
import com.elephant.model.subimagemodel.SubImageModel;
import com.elephant.model.uploadproduct.BulkProductModel;
import com.elephant.model.uploadproduct.ProductModel;
import com.elephant.model.uploadproduct.ProductModel1;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;
import com.elephant.utils.DateUtility;
import com.elephant.utils.FileUtils;



@EnableJpaRepositories
@Service("uploadService")
public class ProductServiceImpl implements ProductService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	
	@Autowired
	ProductDao uploadproductdao;
	
	
	@Autowired
	ProductMapper uploadproductmapper;
	
	@Autowired
	CategoryMapper categoryMapper;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	SubImageDaoRepository subImageDaoRepository;

//	@Override
//	public List<ProductModel> getProductByCatagory(String categoryName, String colors, Float discount, Double length,
//			Double price, String materialType, String fabricPurity, String pattern, String border, String borderType,
//			String zariType, String blouse, String blouseColor,Double blouseLength) throws Exception {
//		try {
//			
//			List<ProductDomain> uploadProductdomain = uploadproductdao.getProductByCatagory( categoryName,  colors, discount, length, price, materialType, fabricPurity,
//					  pattern, border,borderType,zariType,blouse,blouseColor,blouseLength);
//			return uploadproductmapper.entityList(uploadProductdomain);
//		} catch (Exception ex) {
//			logger.info("Exception getAttendanceViewByStandard:", ex);
//		}
//		return null;
//	}

	/*----------------------------------Add Product-------------------------------------*/

	
	@Override
	public Response addproduct(ProductModel model) throws Exception {
		Response response=CommonUtils.getResponseObject("upload products");
		try {
			ProductDomain domain=new ProductDomain();
			BeanUtils.copyProperties(model, domain);
			domain.setProductId(CommonUtils.generateRandomId());
			domain.setQuantity(1);
			domain.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
            domain.setUploadDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
			domain.setActive(true);
			//double cp1=(model.getPrice()-((model.getDiscount()/100)*model.getPrice()));
			double cp1=(model.getPrice()-((model.getDiscount()*model.getPrice())/100));
            domain.setCp(cp1);
			
			
            if(null != model.getCategoryName()) {
            	Category categoryDomain1=categoryRepository.findByCategoryName(model.getCategoryName());
            	if(categoryDomain1 != null) { //isActive need to check
            		domain.setCategory(categoryDomain1);
            	} else {
            		response.setStatus(StatusCode.ERROR.name());
					response.setMessage("Category name is not found");
					return response;
            	}
            }
            
            ProductDomain validateSku = uploadproductdao.isSKUExist(domain.getSku());
            if(validateSku != null){
            	response.setStatus(StatusCode.ERROR.name());
    			response.setMessage("SKU already exists");
    			return response;
            }
            
            domain.setSubImageList(null);
            productRepository.save(domain);
			
			SubImageDomain subImageDomain = null;
			if(model.getSubImageList() != null) {
				for(SubImageModel subImageModel:model.getSubImageList()) {
					subImageDomain = new SubImageDomain();
					BeanUtils.copyProperties(subImageModel, subImageDomain);
					subImageDomain.setProductDomain(domain);
					subImageDaoRepository.save(subImageDomain);
					subImageDomain = null;
				}
				
			}
			
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("product upload success");
			return response;
			
		}
			
   		catch (Exception ex) {
	   			response.setStatus(StatusCode.ERROR.name());
				response.setMessage("Exception "+ex.getMessage());
    		}
    		return response;
    			
    		}
	
	/*----------------------------------Update Product -------------------------------------*/


	@Override
	public Response updateProduct(ProductModel updateproduct) throws Exception {
		try {
			ProductDomain upd = new ProductDomain();
			BeanUtils.copyProperties(updateproduct, upd);
				
			Response response = uploadproductdao.updateProduct(upd);
			return response;

		} catch (Exception ex) {
			logger.info("Exception update product Service:" + ex.getMessage());
		}
		return null;
	}
	/*----------------------------------Upload Excel File-------------------------------------*/


	@Override
	public Response exportExcel(MultipartFile file,BulkProductModel bulkProductModel) throws Exception {
		Response response=CommonUtils.getResponseObject("upload products excel");
		try {
			FileType fileType = FileType.valueOf(FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase());
			switch (fileType) {
			case CSV:
				return saveCSVFile(bulkProductModel,file);
			case XLS:
				return saveXLSFile(bulkProductModel,file);
			case XLSX:
				return saveXLSXFile(bulkProductModel,file);
			default:
				response.setStatus(StatusCode.ERROR.name());
				response.setErrors("The ." + file.getContentType() + " is not supported");
				return response;
			}
		}catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		response.setStatus(StatusCode.ERROR.name());
		response.setErrors("The ." + FilenameUtils.getExtension(file.getOriginalFilename()) + " is not supported");
		return response;
	}

	public Response saveXLSXFile(BulkProductModel bulkProductModel, MultipartFile file) {
		Response response = CommonUtils.getResponseObject("Add xlsx file");
		try {
		    BulkProduct bulk = new BulkProduct();
			bulk = getFileObject(bulkProductModel, file);
			XSSFRow row = null;
			File excel = new File(bulk.getPath());
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			int rowNum = ws.getLastRowNum() + 1;
			
			
			for (int i = 1; i < rowNum; i++) {
				row = ws.getRow(i);
				int colNum = ws.getRow(i).getLastCellNum();
				ProductDomain uploadProduct = new ProductDomain();
				Category category = null;
				SubImageDomain subImageDomain = null;
				List<SubImageDomain> subImageList = new ArrayList<SubImageDomain>();
				for (int j = 0; j < colNum; j++) 
				{
					switch(j+1) 
					{
						case 1:
							uploadProduct.setProductId(CommonUtils.generateRandomId());
							uploadProduct.setUploadDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
							uploadProduct.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
							uploadProduct.setActive(true);
							uploadProduct.setQuantity(1);
							
							row.getCell(j).setCellType(CellType.STRING);
							category=categoryRepository.findByCategoryName(row.getCell(j).getStringCellValue());
							if(category != null) {
								uploadProduct.setCategory(category);
							} else {
								response.setStatus(StatusCode.ERROR.name());
								response.setMessage("Category name "+row.getCell(j).toString()+" is not valid");
								return response;
							}
							
							break;
						case 2:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setSku(row.getCell(j).getStringCellValue());
							break;
	
						case 3:
							row.getCell(j).setCellType(CellType.NUMERIC);
							uploadProduct.setInStock((long)row.getCell(j).getNumericCellValue());break;
							
						case 4:
							row.getCell(j).setCellType(CellType.NUMERIC);
							uploadProduct.setLength(row.getCell(j).getNumericCellValue());break;
							
						case 5:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setPattern(row.getCell(j).getStringCellValue());break;
							
						case 6:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setFabricPurity(row.getCell(j).getStringCellValue());break;
							
						case 7:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setBorder(row.getCell(j).getStringCellValue());break;
							
						case 8:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setBorderType(row.getCell(j).getStringCellValue());break;
							
						case 9:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setZariType(row.getCell(j).getStringCellValue());break;
									
						case 10:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setMaterialType(row.getCell(j).getStringCellValue());break;
						
						case 11:
							row.getCell(j).setCellType(CellType.NUMERIC);
							uploadProduct.setPrice(row.getCell(j).getNumericCellValue());break;
							
						case 12:
							row.getCell(j).setCellType(CellType.NUMERIC);
							uploadProduct.setDiscount((float)row.getCell(j).getNumericCellValue());break;
							
						case 13:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setBlouseColor(row.getCell(j).getStringCellValue());break;		
							
						case 14:
							row.getCell(j).setCellType(CellType.NUMERIC);
							uploadProduct.setBlouseLength(row.getCell(j).getNumericCellValue());break;
							
						case 15:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setColors(row.getCell(j).getStringCellValue());break;
																
						case 16:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setCollectionDesc(row.getCell(j).getStringCellValue());break;
							
						case 17:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setHeaderDesc1(row.getCell(j).getStringCellValue());break;
							
						case 18:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setHeaderDesc2(row.getCell(j).getStringCellValue());break;
							
						case 19:
							row.getCell(j).setCellType(CellType.STRING);
						    uploadProduct.setHeaderDesc3(row.getCell(j).getStringCellValue());break;
							
						case 20:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setHeaderDesc4(row.getCell(j).getStringCellValue());break;
							
						case 21:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setHeaderDesc5(row.getCell(j).getStringCellValue());break;
																	
						case 22:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setOccassion(row.getCell(j).getStringCellValue());break;
							
						case 23:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setProductName(row.getCell(j).getStringCellValue());break;
							
						case 24:
							row.getCell(j).setCellType(CellType.STRING);
							uploadProduct.setMainImageUrl(row.getCell(j).getStringCellValue());break;	
							
						default:	
							row.getCell(j).setCellType(CellType.STRING);
							subImageDomain = null;
							subImageDomain = new SubImageDomain();
							subImageDomain.setImagePath(row.getCell(j).getStringCellValue());
							subImageList.add(subImageDomain);
					}
				}
				
				double cp=(uploadProduct.getPrice()-((uploadProduct.getDiscount()*uploadProduct.getPrice())/100));
				uploadProduct.setCp(cp);
				uploadproductdao.save(uploadProduct);
				subImageDomain = null;
				for(SubImageDomain subImage:subImageList) {
					subImageDomain = new SubImageDomain();
					subImageDomain.setImagePath(subImage.getImagePath());
					subImageDomain.setProductDomain(uploadProduct);
					subImageDaoRepository.save(subImageDomain);
					subImageDomain = null;
				}
			}
			uploadproductdao.saveBulkProduct(bulk);
			
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("Products successfully uploaded");
			
			wb.close();
		}catch (Exception ex) {
			response.setStatus(StatusCode.ERROR.name());
			response.setMessage("Exception "+ex.getMessage());
			logger.info("Exception Service:" + ex.getMessage());
		}
		return response;
	}
	
	public Response saveCSVFile(BulkProductModel bulkProductModel, MultipartFile file) {
		Response response = CommonUtils.getResponseObject("Add CSV file");
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage("Under progress");
		return response;
	}
	
	public Response saveXLSFile(BulkProductModel bulkProductModel, MultipartFile file) {
		Response response = CommonUtils.getResponseObject("Add xls file");
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage("Under progress");
		return response;
	}
	
		
	public BulkProduct getFileObject(BulkProductModel bulkProductModel, MultipartFile file) {
		BulkProduct bulk = new BulkProduct();
		try {
			String filePath = Paths.get(".").toAbsolutePath().toString() +"\\Product Excel";
			BeanUtils.copyProperties(bulkProductModel, bulk);
			bulk.setBulkProductId(CommonUtils.generateRandomId());
			bulk.setName(file.getOriginalFilename());
			bulk.setType(file.getContentType());
			bulk.setSize(file.getSize());
			bulk.setPath(filePath + "/" + bulk.getBulkProductId() + "/" + file.getOriginalFilename());
			bulk.setCreatedDate(new Date());
			bulk.setModifiedDate(new Date());
			FileUtils.createDir(filePath + "/" + bulk.getBulkProductId()) ;
			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filePath + "/" + bulk.getBulkProductId() + "/" + file.getOriginalFilename());
				Files.write(path, bytes);
			} catch (Exception e) {
				logger.info("Exception in getFileObject:" + e.getMessage());
			}
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return bulk;
	}

		
		
	/*@Override
	public List<UploadProductDomain> uploadFile(MultipartFile multipartFile) throws IOException {
		

		File file = convertMultiPartToFile(multipartFile);

		List<UploadProductDomain> mandatoryMissedList = new ArrayList<UploadProductDomain>();

		try (Reader reader = new FileReader(file);) {
			CsvToBean<UploadProductDomain> csvToBean = new CsvToBeanBuilder<UploadProductDomain>(reader).withType(UploadProductDomain.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			List<UploadProductDomain> productList = csvToBean.parse();

			Iterator<UploadProductDomain> questionListClone = productList.iterator();

			while (questionListClone.hasNext()) {

				UploadProductDomain product = questionListClone.next();
				System.out.println(product.getProductId());
				if(product.getProductId() == null) {
				mandatoryMissedList.add(product);
				questionListClone.remove();
				}
				
			}

			uploadproductdao.uploadFile(productList);
			
		}
		catch(Exception e) {
			logger.info("exception"+ e.getMessage());
			
		}
		return mandatoryMissedList;
		
	}
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@Override
	public void exportExcel(MultipartFile file,String categoryName) throws Exception {

		XSSFWorkbook workbook = null;
			try {
				
				InputStream inputStream = file.getInputStream(); 	
				workbook = new XSSFWorkbook(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) 
			{
				Row row=rowIterator.next();
				if(row.getRowNum()==0)
					continue;

				UploadProductDomain uploadProduct = new UploadProductDomain();
				
				Iterator<Cell> cellIterator = row.cellIterator();
				int index;
				while(cellIterator.hasNext()) 
				{
					Cell cell = cellIterator.next();

					index= cell.getColumnIndex();
					System.out.println(index);
					switch(index+1) 
					{
					
					case :cell.setCellType(Cell.CELL_TYPE_STRING);
					
					uploadProduct.setProductId(CommonUtils.generateRandomId());
					uploadProduct.setUploadDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
					uploadProduct.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
					uploadProduct.setActive(true);
					uploadProduct.setBlouseColor(cell.getStringCellValue());
					uploadProduct.setBorder(cell.getStringCellValue());
					uploadProduct.setBorderType(cell.getStringCellValue());
					uploadProduct.setCollectionDesc(cell.getStringCellValue());
					uploadProduct.setColors(cell.getStringCellValue());
					uploadProduct.setFabricPurity(cell.getStringCellValue());
					uploadProduct.setMainImageUrl(cell.getStringCellValue());
					uploadProduct.setMaterialType(cell.getStringCellValue());
					uploadProduct.setOccassion(cell.getStringCellValue());
					uploadProduct.setOtherImageUrl1(cell.getStringCellValue());
					uploadProduct.setOtherImageUrl2(cell.getStringCellValue());
					uploadProduct.setPattern(cell.getStringCellValue());
					uploadProduct.setSku(cell.getStringCellValue());
					uploadProduct.setSubImageUrl1(cell.getStringCellValue());
					uploadProduct.setSubImageUrl2(cell.getStringCellValue());
					uploadProduct.setSubImageUrl3(cell.getStringCellValue());
					uploadProduct.setSubImageUrl4(cell.getStringCellValue());
					uploadProduct.setSubImageUrl5(cell.getStringCellValue());
					uploadProduct.setSubImageUrl6(cell.getStringCellValue());
					uploadProduct.setTypes(cell.getStringCellValue());
					uploadProduct.setZariType(cell.getStringCellValue());
					uploadProduct.setBlouse(cell.getStringCellValue());break;
					
					
					
					case 2:cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					uploadProduct.setQuantity((long)(cell.getNumericCellValue()));
					uploadProduct.setPrice((double)cell.getNumericCellValue());
					uploadProduct.setLength((double)(cell.getNumericCellValue()));
					uploadProduct.setDiscount((float)cell.getNumericCellValue());
					uploadProduct.setBlouseLength((double) cell.getNumericCellValue());break;
					
					
					case 1:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setProductId(CommonUtils.generateRandomId());
						uploadProduct.setUploadDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
						uploadProduct.setModifiedDate(DateUtility.getDateByStringFormat(new Date(), DateUtility.DATE_FORMAT_DD_MMM_YYYY_HHMMSS));
						uploadProduct.setActive(true);
						uploadProduct.setBlouse(cell.getStringCellValue());break;
					case 2:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setBlouseColor(cell.getStringCellValue());break;

					case 3:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						uploadProduct.setBlouseLength((double) cell.getNumericCellValue());break;
						
					case 4:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setBorder(cell.getStringCellValue());break;
						
					case 5:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setBorderType(cell.getStringCellValue());break;
						
					case 6:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setCollectionDesc(cell.getStringCellValue());break;
						
					case 7:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setColors(cell.getStringCellValue());break;
						
					case 8:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						uploadProduct.setDiscount((float)cell.getNumericCellValue());break;
							
						
					case 9:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setFabricPurity(cell.getStringCellValue());break;
						
								
					case 10:
						cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
						uploadProduct.setActive(true);break;
					
					case 11:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						uploadProduct.setLength((double)(cell.getNumericCellValue()));break;
						
					case 12:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setMainImageUrl(cell.getStringCellValue());break;
						
					case 13:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setMaterialType(cell.getStringCellValue());break;		
						
					case 14:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setOccassion(cell.getStringCellValue());break;
						
					case 15:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setOtherImageUrls(cell.getStringCellValue());break;
															
					case 17:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setPattern(cell.getStringCellValue());break;
																
					case 18:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						uploadProduct.setPrice((double)cell.getNumericCellValue());break;
						
					case 19:
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						uploadProduct.setQuantity((long)(cell.getNumericCellValue()));break;
					
					case 20:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setSku(cell.getStringCellValue());break;
				
									
								
					case 28:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						uploadProduct.setZariType(cell.getStringCellValue());break;
							
					
					}
					
					uploadproductdao.save(uploadProduct);
					
				}
				
			}
	}*/
		
		

	
	
	/*---------------------------------delete product by id-----------------------------------*/
	@Override
	public Response deleteproduct(String productId) throws Exception {
try {
			return uploadproductdao.deleteproduct(productId);
		} catch (Exception e) {
			logger.info("Exception deleteproduct:", e);
			return null;
		}
	}
	
	@Override
	public Response deleteProduct(String productId, boolean isActive) throws Exception {
		try {
			return uploadproductdao.deleteProduct(productId,isActive);
		} catch (Exception e) {
			logger.info("Exception deleteproduct:", e);
			return null;
		}
	}
	
	/*----------------------------------Get Product By Id-------------------------------------*/

	

	@Override
	public ProductModel getProductById(String productId) throws Exception {
		try {
			
			ProductDomain up = uploadproductdao.getProductById(productId);
			ProductModel productModel = new ProductModel();
			if (up == null)
				return null;
			
			List<SubImageDomain> subImageList = up.getSubImageList();
			for(SubImageDomain subImageDomain:subImageList) {
				subImageDomain.setProductDomain(null);
			}
			BeanUtils.copyProperties(up, productModel);
			productModel.setCategoryName(up.getCategory().getCategoryName());
			
			return productModel;
		} catch (Exception e) {
			logger.info("Exception getUser:", e.getMessage());
			return null;
		}
	}

	
	@Override
	public List<ProductModel> getProductByDiscount(float discount) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByDiscount(discount);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getproductDiscount:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByColor(String colors) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByColor(colors);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getproductType:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByOccassion(String occassion) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByOccassion(occassion);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByOccassion:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByPrice(double price) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByPrice(price);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getproductType:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByPriceRange(double minPrice,double maxPrice) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByPriceRange(minPrice,maxPrice);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getproductRange:", e);
		
		}
		return null;
	}
	
	@Override
	public List<ProductModel> getProductsByPriceRange(String categoryName,double minPrice,double maxPrice) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductsByPriceRange(categoryName,minPrice,maxPrice);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getproductRange:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByMaterialType(String materialType) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByMaterialType(materialType);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByMaterialType:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByBorder(String border) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByBorder(border);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByBorder:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByBorderType(String borderType) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByBorderType(borderType);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByBorderType:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByZariType(String zariType) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByZariType(zariType);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByZariType:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByBlouse(String blouse) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByBlouse(blouse);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByBlouse:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByPattern(String pattern) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByPattern(pattern);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByPattern:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByFabricPurity(String fabricPurity) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByFabricPurity(fabricPurity);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByFabricPurity:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByBlouseColor(String blouseColor) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByBlouseColor(blouseColor);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByBlouseColor:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByBlouseLength(Double blouseLength) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByBlouseLength(blouseLength);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByBlouseLength:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductBySareeLength(Double length) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductBySareeLength(length);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductBySareeLength:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProducts() throws Exception {
		try {
			List<ProductDomain> product=uploadproductdao.getProducts();
			return uploadproductmapper.entityList(product);
		}
		catch(Exception e) {logger.info("Exception getProducts:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByFilterDiscount(float discount) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByFilterDiscount(discount);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getproductDiscount:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getPriceSortingLowToHigh() throws Exception {
		try {
			List<ProductDomain> product=uploadproductdao.getPriceSortingLowToHigh();
			return uploadproductmapper.entityList(product);
		}
		catch(Exception e) {logger.info("Exception getProducts:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getPriceSortingHighToLow() throws Exception {
		try {
			List<ProductDomain> product=uploadproductdao.getPriceSortingHighToLow();
			return uploadproductmapper.entityList(product);
		}
		catch(Exception e) {logger.info("Exception getProducts:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByTypesSortingPriceLowToHigh(String types) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByTypesSortingPriceLowToHigh(types);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByCategoryNameSortingPriceLowToHigh:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductByTypesSortingPriceHighToLow(String types) throws Exception {
		try {
			List<ProductDomain> upload=uploadproductdao.getProductByTypesSortingPriceHighToLow(types);
			return uploadproductmapper.entityList(upload);
		}
		catch(Exception e) {logger.info("Exception getProductByCategoryNameSortingPriceHighToLow:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getByNewProducts() throws Exception {
		try {
			List<ProductDomain> product=uploadproductdao.getByNewProducts();
			return uploadproductmapper.entityList(product);
		}
		catch(Exception e) {logger.info("Exception getByNewProducts:", e);
		
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductBycategoryId(String categoryId) throws IOException {
	try {
	List<ProductDomain> up = uploadproductdao.getProductBycategoryId(categoryId);
	return uploadproductmapper.entityList(up);
		} catch (Exception e) {
			logger.info("Exception getUser:", e.getMessage());
			return null;
		}
	}

	@Override
	public List<ProductModel> getProductByCatagory1(ProductModel1 pm1) {
		try {
			
			List<ProductDomain> uploadProductdomain = uploadproductdao.getProductByCatagory1(pm1);
			return uploadproductmapper.entityList(uploadProductdomain);
		} catch (Exception ex) {
			logger.info("Exception getAttendanceViewByStandard:", ex);
		}
		return null;
	}


	public void createExcelTemplate(File file) {
		try {
			String[] columnshead = {"Category_Name","SKU","In Stock","Saree Length","Pattern","Fabric Purity","Border","Border Type","Zari Type","Material Type","Price", 
					"Discount","Blouse Color","Blouse Length","Saree Colors","Collection Desc","Header Desc1","Header Desc2","Header Desc3","Header Desc4","Header Desc5",
					"Occassion","Product Name","Main ImageUrl","Sub Image1"};
			Workbook workbook = new XSSFWorkbook();
			XSSFSheet  sheet = (XSSFSheet) workbook.createSheet("Product_Upload_Template");
			 
			 Font headerFont = workbook.createFont();
		        headerFont.setBold(true);
		        headerFont.setFontHeightInPoints((short) 11);
		        headerFont.setColor(IndexedColors.BLACK.getIndex());
		        headerFont.setFontName("Arial");
		        
		     CellStyle headerCellStyle = workbook.createCellStyle();
		        headerCellStyle.setFont(headerFont);
		        Row headerRow = sheet.createRow(0);
		       
		        
		        for(int i = 0; i < columnshead.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnshead[i]);
		            cell.setCellStyle(headerCellStyle);
		        }
		        
		        for(int i = 0; i < columnshead.length; i++) {
		            sheet.autoSizeColumn(i);
		        }
		        FileOutputStream fileOut = new FileOutputStream(file);
		        workbook.write(fileOut);
		        fileOut.close();
		        workbook.close();
		        fileOut.flush();
		} catch (Exception e) {
			throw new RuntimeException("FAIL!"+e);
		}
		
	}

	@Override
	public Resource getExcelForBulkProduct() throws Exception {
		try {
			//String filePath = Paths.get(".").toAbsolutePath().toString() +"\\ExcelTemplate";
			String filePath = "/home/ubuntu/Projects/" +"ExcelTemplate";
			System.out.println(filePath);
			//Path file = Paths.get(filePath+"\\Template.xlsx");
			Path file = Paths.get(filePath+"/Template.xlsx");
			
			//File fileNew = new File(filePath+"\\Template.xlsx");
			File fileNew = new File(filePath+"/Template.xlsx");
			
			boolean exists = fileNew.exists();
			if(!exists) {
				new File(filePath).mkdirs();
				createExcelTemplate(fileNew);
			}
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (Exception e) {
			logger.info("Exception getExcelForBulkProduct:", e);
			throw new RuntimeException("FAIL!");
		}
	}


	@Override
	public ByteArrayInputStream prdExportToExcel(String categoryId) throws Exception {
		List<ProductModel> productModelList = null;
		String[] columnshead = {"Category_Name","SKU","Stock","Price","Discount","Blouse Color","Border","Border Type","Collection Desc","Colors","Fabric Purity",
				"Header Desc1","Header Desc2","Header Desc3","Header Desc4","Header Desc5","Saree Length","Material Type","Occassion","Pattern","Zari Type",
				"Blouse Length","Product Name","Main ImageUrl"};
		try {
			if(null != categoryId) {
				productModelList = getProductBycategoryId(categoryId);
					
				//Blank workbook
			        XSSFWorkbook workbook = new XSSFWorkbook();
			        
			        ByteArrayOutputStream out = new ByteArrayOutputStream();
			        
			        //Create a blank sheet
			        XSSFSheet sheet = workbook.createSheet("Product Data");
			        
			        Font headerFont = workbook.createFont();
			        headerFont.setBold(true);
			        headerFont.setFontHeightInPoints((short) 11);
			        headerFont.setColor(IndexedColors.BLACK.getIndex());
			        headerFont.setFontName("Arial");
			        
			        CellStyle headerCellStyle = workbook.createCellStyle();
			        headerCellStyle.setFont(headerFont);
			        
			        Row headerRow = sheet.createRow(0);
			       
			        for(int i = 0; i < columnshead.length; i++) {
			            Cell cell = headerRow.createCell(i);
			            cell.setCellValue(columnshead[i]);
			            cell.setCellStyle(headerCellStyle);
			        }
			        
			        for(int i = 0; i < columnshead.length; i++) {
			            sheet.autoSizeColumn(i);
			        }
			        
			        Row row = null;
			        Cell rowCell = null;

			        for(int count=0;count<productModelList.size();count++) {
			        	row = sheet.createRow(count+1);
			        	for(int j=0;j<columnshead.length;j++) {
			        		rowCell = row.createCell(j);
			        		switch(j+1) {
			        			case 1: rowCell.setCellValue(productModelList.get(count).getCategoryName());
			        					break;
			        			case 2: rowCell.setCellValue(productModelList.get(count).getSku());
			        					break;
			        			case 3: rowCell.setCellValue(productModelList.get(count).getInStock());
			        					break;
			        			case 4: rowCell.setCellValue(productModelList.get(count).getPrice());
			        					break;			 
			        			case 5: rowCell.setCellValue(productModelList.get(count).getDiscount());
			        					break;
			        			case 6: rowCell.setCellValue(productModelList.get(count).getBlouseColor());
			        					break;
			        			case 7: rowCell.setCellValue(productModelList.get(count).getBorder());
			        					break;
			        			case 8: rowCell.setCellValue(productModelList.get(count).getBorderType());
			        					break;
			        			case 9: rowCell.setCellValue(productModelList.get(count).getCollectionDesc());
			        					break;
			        			case 10: rowCell.setCellValue(productModelList.get(count).getColors());
			        					 break;
			        			case 11: rowCell.setCellValue(productModelList.get(count).getFabricPurity());
			        					 break;
			        			case 12: rowCell.setCellValue(productModelList.get(count).getHeaderDesc1());
			        			         break;
			        			case 13: rowCell.setCellValue(productModelList.get(count).getHeaderDesc2());
			        					 break;
			        			case 14: rowCell.setCellValue(productModelList.get(count).getHeaderDesc3());
	        			         		 break;
			        			case 15: rowCell.setCellValue(productModelList.get(count).getHeaderDesc4());
	        			         		 break;
			        			case 16: rowCell.setCellValue(productModelList.get(count).getHeaderDesc5());
	        			         	     break;
			        			case 17: rowCell.setCellValue(productModelList.get(count).getLength());
			        					 break;
			        			case 18: rowCell.setCellValue(productModelList.get(count).getMaterialType());
			        					 break;
			        			case 19: rowCell.setCellValue(productModelList.get(count).getOccassion());
			        					 break;
			        			case 20: rowCell.setCellValue(productModelList.get(count).getPattern());
			        			  		 break;
			        			case 21: rowCell.setCellValue(productModelList.get(count).getZariType());
			        					 break;
			        			case 22: rowCell.setCellValue(productModelList.get(count).getBlouseLength());
			        					 break;
			        			case 23: rowCell.setCellValue(productModelList.get(count).getProductName());
			        			         break;
			        			case 24: rowCell.setCellValue(productModelList.get(count).getMainImageUrl());
			        					 break;
			        			         
			        		}
			        	}
			        }
			        
			        workbook.write(out);
			        workbook.close();
			        return new ByteArrayInputStream(out.toByteArray());
			        		
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public Float[] getProductsDiscounts() {
		Float[] discount = uploadproductdao.getProductDiscounts();
		return discount;
	}

	@Override
	public List<ProductModel> getProductsByName(String productName) throws Exception {
		try {
			List<ProductDomain> up = uploadproductdao.getProductByName(productName);
			return uploadproductmapper.entityList(up);
			} catch (Exception e) {
				logger.info("Exception getProductsByName:"+e);
				return null;
			}
	}

	
	

	
}
	
	
	
	