package com.elephant.dao.subimage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.subimages.SubImageDomain;

public interface SubImageDaoRepository extends JpaRepository<SubImageDomain, Long> {
	public SubImageDomain findBysubImageId(long subImageId);
}
