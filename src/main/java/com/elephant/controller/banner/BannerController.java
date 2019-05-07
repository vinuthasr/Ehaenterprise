package com.elephant.controller.banner;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elephant.constant.StatusCode;
import com.elephant.model.banner.BannerModel;
import com.elephant.response.ErrorObject;
import com.elephant.response.Response;
import com.elephant.service.banner.BannerService;
import com.elephant.utils.CommonUtils;


@RestController
@RequestMapping("/v1/banner")
@CrossOrigin(origins= {"https://eha-admin-v1.herokuapp.com","http://localhost:4200","https://eha-user-app.herokuapp.com"})
public class BannerController {

	private static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	@Autowired
	public BannerService bannerService;
	
	//================create banner=========
	@RequestMapping(value="/post",method = RequestMethod.POST, produces = "application/json")
	public Response createBanner(@RequestBody BannerModel bannerModel ,HttpServletRequest request,HttpServletResponse response) {
		return bannerService.createBanner(bannerModel);
	}
	
	//================= delete banner=========
    @RequestMapping(value = "/delete/{bannerId}",method = RequestMethod.DELETE, produces = "application/json")
    public Response deleteBannerById(@PathVariable("bannerId") long bannerId){
    	return bannerService.deleteBannerById(bannerId);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT, produces = "application/json")
    public Response updateBannerById(@RequestBody BannerModel bannerModel){
        return bannerService.updateBannerById(bannerModel);
    }

    @RequestMapping(value = "/get/{bannerId}",method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getBannerById(@PathVariable("bannerId") long bannerId){
       
    	BannerModel bannerModel = bannerService.getBannerById(bannerId);
		Response res = CommonUtils.getResponseObject("Banner Details");
		if (bannerModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Banners Not Found", "Banners Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(bannerModel);
		}
		logger.info("getbanner: Sent response");
		return CommonUtils.getJson(res);
    	
    	
    }

    @RequestMapping(value = "/getall",method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getAllBanners(HttpServletRequest request,HttpServletResponse response){
       
    	List<BannerModel> bannerModel = bannerService.getAllBanners();
		Response res = CommonUtils.getResponseObject("List of Banners");
		if (bannerModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Banners Not Found", "Banners Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(bannerModel);
		}
		logger.info("getUsers: Sent response");
		return CommonUtils.getJson(res);
    	
    }
	
}