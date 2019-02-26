package com.elephant.dao.image;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.image.ImageDomain;

public interface ImageDaoRepository extends JpaRepository<ImageDomain, Long> {

	public ImageDomain findByImageId(long imageId);
}
