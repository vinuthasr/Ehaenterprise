package com.elephant.mapper.image;

import org.springframework.stereotype.Component;

import com.elephant.mapper.AbstractModelMapper;
import com.elephant.domain.image.ImageDomain;
import com.elephant.model.image.ImageModel;


@Component
public class ImageMapper extends AbstractModelMapper<ImageModel,ImageDomain>{
	
	@Override
	public Class<ImageModel> entityType() {
		return ImageModel.class;
	}

	@Override
	public Class<ImageDomain> modelType() {
		
		return ImageDomain.class;
	}

}

