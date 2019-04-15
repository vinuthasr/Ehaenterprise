package com.elephant.dao.image;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elephant.constant.StatusCode;
import com.elephant.domain.banner.BannerDomain;
import com.elephant.domain.image.ImageDomain;
import com.elephant.response.Response;
import com.elephant.utils.CommonUtils;

@Transactional
@Repository
public class ImageDaoImpl implements ImageDao{

	private static final Logger logger= LoggerFactory.getLogger(ImageDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Response postImage(ImageDomain imageDomain) throws Exception {
		try {
		entityManager.persist(imageDomain);
		}
		catch(Exception e)
		{
			System.out.println("exception in post");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageDomain> allImage() {
		try {
		String hql="from ImageDomain";
		return (List<ImageDomain>)entityManager.createQuery(hql).getResultList();
		}
		catch(Exception e) {
			logger.info("error:"+e.getMessage());
		}
		return null;
	}

	@Override
	public ImageDomain getImage(long imageId) throws Exception {
		try {
		String hql="from ImageDomain where imageId=?1";
		return (ImageDomain) entityManager.createQuery(hql).setParameter(1, imageId).getSingleResult();
		}
		catch(Exception e) {
			logger.info("error:"+e.getMessage());
		}
		return null;
	}

	@Override
	public Response delete(long imageId) throws Exception {
		Response response = CommonUtils.getResponseObject("deleted");
		try {
		String hql="Delete from ImageDomain where imageId=?1";
		entityManager.createQuery(hql).setParameter(1, imageId).executeUpdate();
		response.setStatus(StatusCode.SUCCESS.name());
		return response;
		}
		catch(Exception e) {
			logger.info("error",e.getMessage());
		}
		return null;
	}

	@Override
	public Response update(ImageDomain imageDomain) throws Exception {
		Response response = CommonUtils.getResponseObject("Update customer data");
		try {
			ImageDomain i=getImage(imageDomain.getImageId());
			i.setImageName(imageDomain.getImageName());
			i.setImagePath(imageDomain.getImagePath());
			i.setHeader(imageDomain.getHeader());
			i.setCreatedDate(imageDomain.getCreatedDate());
			i.setModifiedDate(imageDomain.getModifiedDate());
			i.setDesc1(imageDomain.getDesc1());
			i.setDesc2(imageDomain.getDesc2());
			i.setDesc3(imageDomain.getDesc3());
			i.setDesc4(imageDomain.getDesc4());
			entityManager.flush();
			response.setStatus(StatusCode.SUCCESS.name());
			
		}
		catch(Exception e) {
			logger.info("error",e.getMessage());
		}
		return null;
	}

	@Override
	public ImageDomain getPath(String imagePath) throws Exception {
		try {
		String hql="from ImageDomain where imagePath=?1";
		return (ImageDomain) entityManager.createQuery(hql).setParameter(1, imagePath).getSingleResult();
		}
		catch(Exception e) {
			System.out.println("exception");
		}
		return null;
	}

	@Override
	public boolean isRecordExist(BannerDomain bannerDomain, int imageSequenceNo) {
		try {
			String hql="from ImageDomain where bannerDomain=?1 and imageSequenceNo=?2";
			ImageDomain domain = (ImageDomain) entityManager.createQuery(hql)
					.setParameter(1, bannerDomain)
					.setParameter(2, imageSequenceNo).getSingleResult();
			if(domain != null)
				return true;
			}
			catch(Exception e) {
				System.out.println("exception "+e);
			}
		return false;
	}

	
}

