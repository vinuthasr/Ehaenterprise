package com.elephant.service.banner;

import java.util.List;

import com.elephant.model.banner.BannerModel;
import com.elephant.response.Response;

public interface BannerService {

public Response createBanner(BannerModel bannerModel);
	
	public Response deleteBannerById(long bannerId);
	
	public Response updateBannerById(BannerModel bannerModel);
	
	public BannerModel getBannerById(long bannerId);
	
	public List<BannerModel> getAllBanners();
	
}

