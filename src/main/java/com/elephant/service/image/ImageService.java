package com.elephant.service.image;

import java.util.List;

import com.elephant.domain.image.ImageDomain;
import com.elephant.model.image.ImageModel;
import com.elephant.response.Response;


public interface ImageService {

	public Response postImage(ImageModel imageModel,String bannerArea)throws Exception;

	public List<ImageModel> allImage()throws Exception;

	public ImageModel getImage(long imageId)throws Exception;

	public Response delete(long imageId)throws Exception;

	public Response update(ImageModel imageModel)throws Exception;

	public ImageDomain getPath(String imagePath)throws Exception;

	List<ImageModel> getImageByBannerArea(String bannerArea);

}

