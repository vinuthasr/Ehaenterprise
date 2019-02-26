package com.elephant.dao.banner;

import java.util.List;

import com.elephant.domain.banner.BannerDomain;
import com.elephant.response.Response;

public interface BannerDao {
	public Response createBanner(BannerDomain bannerDomain);
	
	public Response deleteBannerById(long bannerId);
	public Response updateBannerById(BannerDomain bannerDomain);
	public BannerDomain getBannerById(long bannerId);
	public List<BannerDomain> getAllBanners();
	
	
}
