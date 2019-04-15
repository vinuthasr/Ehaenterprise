package com.elephant.service.image;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elephant.constant.Constants;
import com.elephant.constant.StatusCode;
import com.elephant.dao.banner.BannerRepository;
import com.elephant.dao.category.CategoryRepository;
import com.elephant.dao.image.ImageDao;
import com.elephant.dao.image.ImageDaoRepository;
import com.elephant.dao.subimage.SubImageDaoRepository;
import com.elephant.domain.banner.BannerDomain;
import com.elephant.domain.category.Category;
import com.elephant.domain.image.ImageDomain;
import com.elephant.mapper.image.ImageMapper;
import com.elephant.model.image.ImageModel;
import com.elephant.response.Response;
import com.elephant.service.banner.BannerService;
import com.elephant.utils.CommonUtils;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	 ImageDao imageDao;
	
	@Autowired
	 ImageMapper imageMapper;
	
	@Autowired
	BannerService bannerService;
	
	@Autowired
	BannerRepository bannerRepository;
	
	@Autowired
	ImageDaoRepository imageDaoRepository ;
	

	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Response postImage(ImageModel imageModel)throws Exception{
		Response response=CommonUtils.getResponseObject("Post Image");
		String bannerArea = imageModel.getBannerModel().getBannerArea();
		String categoryName = imageModel.getCategoryDomain().getCategoryName();
		try {
		ImageDomain imageDomain=new ImageDomain();
		BeanUtils.copyProperties(imageModel, imageDomain);
		BannerDomain bannerDomain = null;
		if(null != bannerArea) {
		    bannerDomain=bannerRepository.findByBannerArea(bannerArea);
			if(bannerDomain != null) {
				imageDomain.setBanner(bannerDomain);				
			} else {
				response.setStatus(StatusCode.ERROR.name());
				response.setMessage("Banner Area is Not found");
				return response;
			}
			
		}
        
		if(null != categoryName) {
			Category categoryDomain = categoryRepository.findByCategoryName(categoryName);
			if(categoryDomain != null) {
				imageDomain.setCategoryDomain(categoryDomain);
			} else {
				response.setStatus(StatusCode.ERROR.name());
				response.setMessage("Category Name is Not found");
				return response;
			}
		}
		
		if(imageModel.getImageSequenceNo() != 0) {
			if(!bannerArea.equalsIgnoreCase(Constants.BANNER_SLIDER_AREA)) {
				boolean isRecordExist = imageDao.isRecordExist(bannerDomain, imageModel.getImageSequenceNo());
				if(isRecordExist ==  true) {
					response.setStatus(StatusCode.ERROR.name());
					response.setMessage("Image Sequence number already exist");
					return response;
				}
			}
		}
		
		imageDaoRepository.save(imageDomain);
		
		response.setStatus(StatusCode.SUCCESS.name());
		response.setMessage("Image Post is Successfull");
		
//		List<BannerDomain> bannerDomainList=bannerRepository.findAll();
//		for(int i=0;i< bannerDomainList.size();i++) {
//			if((bannerArea.equals(bannerDomainList.get(i).getBannerArea()))){
//				BannerDomain bannerDomain=bannerRepository.findByBannerArea(bannerArea);
//				imageDomain.setBanner(bannerDomain);
//				imageDaoRepository.save(imageDomain);
//				response.setStatus(StatusCode.SUCCESS.name());
//				response.setMessage("Image Post is Successfull");
//				//Response response=imageDao.postImage(imageDomain);
//				return response;
//			}
//		}
//		response.setStatus(StatusCode.ERROR.name());
//		response.setMessage("Banner Area is Not found");
		return response;
		
		}
		catch(Exception ex) {
			response.setStatus(StatusCode.ERROR.name());
			System.out.println("Exception in post image"+ex);
		}
		return  response;
	}

	@Override
	public List<ImageModel> allImage() throws Exception {
		try {
		List<ImageDomain> image= imageDao.allImage();
		return imageMapper.entityList(image);
		}
		catch(Exception e) {
			
		}
		return null;
	}

	@Override
	public ImageModel getImage(long imageId) throws Exception {
		try {
		ImageDomain imageDomain=imageDao.getImage(imageId);
		ImageModel imageModel=new ImageModel();
		BeanUtils.copyProperties(imageDomain, imageModel);
		return imageModel;
		}
		catch(Exception ex) {
			System.out.println("Exception in get image"+ex);
		}
		
		return null;
	}

	@Override
	public Response delete(long imageId) throws Exception {
		return imageDao.delete(imageId);
	}

	@Override
	public Response update(ImageModel imageModel) throws Exception {
		ImageDomain imageDomain= new ImageDomain();
		BeanUtils.copyProperties(imageModel, imageDomain);
		Response response=imageDao.update(imageDomain);
		return response;
	}

	@Override
	public ImageDomain getPath(String imagePath) throws Exception {
		ImageDomain	image=imageDao.getPath(imagePath);
		return image;
	}
	
	@Override
	public List<ImageModel> getImageByBannerArea(String bannerArea){
		try {
			BannerDomain bannerDomain=bannerRepository.findByBannerArea(bannerArea);
			List<ImageDomain> image= bannerDomain.getImageDomain();
			return imageMapper.entityList(image);
			}
			catch(Exception e) {
				
			}
			return null;
		}
	
	
	

}

/*---------------------------------------------------------Original Code--------------------------------------------------------------*/
/*@Override
public Response postImage(ImageModel imageModel,String bannerArea) throws Exception {
	Response response=CommonUtils.getResponseObject("Post Image");
	
	try {
	ImageDomain imageDomain=new ImageDomain();
	BeanUtils.copyProperties(imageModel, imageDomain);
	List<BannerDomain> bannerDomainList=bannerRepository.findAll();
	for(int i=0;i< bannerDomainList.size();i++) {
		if((bannerArea.equals(bannerDomainList.get(i).getBannerArea()))){
			BannerDomain bannerDomain=bannerRepository.findByBannerArea(bannerArea);
			imageDomain.setBanner(bannerDomain);
			imageDaoRepository.save(imageDomain);
			response.setStatus(StatusCode.SUCCESS.name());
			response.setMessage("Image Post is Successfull");
			//Response response=imageDao.postImage(imageDomain);
			return response;
		}
	}
	response.setStatus(StatusCode.ERROR.name());
	response.setMessage("Banner Area is Not found");
	return response;
	
	}
	catch(Exception ex) {
		response.setStatus(StatusCode.ERROR.name());
		System.out.println("Exception in post image"+ex);
	}
	return  response;
}*/
/*--------------------------------------------------------------------------------------------------------------------------------------*/
