package com.elephant.mapper.subimage;

import org.springframework.stereotype.Component;

import com.elephant.domain.subimages.SubImageDomain;
import com.elephant.mapper.AbstractModelMapper;
import com.elephant.model.subimagemodel.SubImageModel;

@Component
public class SubImageMapper extends AbstractModelMapper<SubImageModel, SubImageDomain> {

	@Override
	public Class<SubImageModel> entityType() {
		// TODO Auto-generated method stub
		return SubImageModel.class;
	}

	@Override
	public Class<SubImageDomain> modelType() {
		// TODO Auto-generated method stub
		return SubImageDomain.class;
	}

}
