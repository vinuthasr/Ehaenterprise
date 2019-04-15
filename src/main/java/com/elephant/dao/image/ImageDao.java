package com.elephant.dao.image;

import java.util.List;

import com.elephant.domain.banner.BannerDomain;
import com.elephant.domain.image.ImageDomain;
import com.elephant.response.Response;


public interface ImageDao {

	public Response postImage(ImageDomain imageDomain)throws Exception;

	public List<ImageDomain> allImage();

	public ImageDomain getImage(long imageId)throws Exception;

	public Response delete(long imageId)throws Exception;

	public Response update(ImageDomain imageDomain)throws Exception;

	public ImageDomain getPath(String imagePath)throws Exception ;

    public boolean isRecordExist(BannerDomain bannerDomain,int imageSequenceNo);
}

