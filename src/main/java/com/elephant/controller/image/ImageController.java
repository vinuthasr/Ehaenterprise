package com.elephant.controller.image;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.domain.image.ImageDomain;
import com.elephant.model.banner.BannerModel;
import com.elephant.model.image.ImageModel;
import com.elephant.model.subimagemodel.SubImageModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.image.ImageService;
import com.elephant.utils.CommonUtils;

@RestController
@RequestMapping(value="/v1/control")
@CrossOrigin(origins= {"https://eha-admin-app.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com/"})
public class ImageController {
	
	public static final Logger logger =LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	public ImageService imageService;
	
	//=======================post image====================
	@RequestMapping(value="/post/ImageModel",method = RequestMethod.POST, produces = "application/json")
	public Response postImage(@RequestBody ImageModel imageModel,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("addimage: Received request URL:" + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addimage: Received Request: " + CommonUtils.getJson(imageModel));
		
		return imageService.postImage(imageModel);
	}
	
	//=====================get all image=================
	@RequestMapping(value="/getall",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String allImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("getimage List: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ImageModel> imageModel = imageService.allImage();
		Response res = CommonUtils.getResponseObject("List of images");
		if (imageModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("images Not Found", "images Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(imageModel);
		}
		logger.info("getCustomers: Sent response");
		return CommonUtils.getJson(res);
	}
	
	//==================get image by id==================
	@RequestMapping(value="/get/{imageId}",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getImage(@PathVariable("imageId")long imageId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("getimage List: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		ImageModel imageModel = imageService.getImage(imageId);
		Response res = CommonUtils.getResponseObject("List of images");
		if (imageModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("images Not Found", "images Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(imageModel);
		}
		logger.info("getCustomers: Sent response");
		return CommonUtils.getJson(res);
	}
	
	//==================delete by id======================
	@RequestMapping(value="/delete/{imageId}",method = RequestMethod.DELETE, produces = "application/json")
	public Response delete(@PathVariable("imageId")long imageId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("deleteimage: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		return imageService.delete(imageId);
	}
	
	//==================update image====================
	@RequestMapping(value="/update",method = RequestMethod.PUT, produces = "application/json")
	public Response update(@RequestBody ImageModel imageModel,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("updateimage: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateimage: Received request: " + CommonUtils.getJson(imageModel));
		return imageService.update(imageModel);
		
	}
	@RequestMapping(value="/getit/{imagePath}",method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ImageDomain getPath(@PathVariable("imagePath")String imagePath,HttpServletRequest request,HttpServletResponse response) throws Exception {
		return imageService.getPath(imagePath);
	}
	
	
	
	
	@RequestMapping(value="/getImageByBannerArea/{bannerArea}",method = RequestMethod.GET, produces = "application/json")
	public String getImageByBannerArea(@PathVariable(value="bannerArea") String bannerArea) {
		List<ImageModel> imageModel = imageService.getImageByBannerArea(bannerArea);
		Response res = CommonUtils.getResponseObject("List of images");
		if (imageModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("images Not Found", "images Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(imageModel);
		}
		logger.info("getCustomers: Sent response");
		return CommonUtils.getJson(res);
	}
	
	
//	//=======================post image====================
//		@RequestMapping(value="/post/ImageModel/{bannerArea}",method = RequestMethod.POST, produces = "application/json")
//		public Response postImage(@RequestBody ImageModel imageModel  ,@RequestParam(value="bannerArea") String bannerArea ,HttpServletRequest request,HttpServletResponse response) throws Exception {
//			logger.info("addimage: Received request URL:" + request.getRequestURL().toString()
//					+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
//			logger.info("addimage: Received Request: " + CommonUtils.getJson(imageModel));
//			return imageService.postImage(imageModel,bannerArea);
//		}
	
}
