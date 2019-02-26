package com.elephant.dao.banner;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elephant.domain.banner.BannerDomain;

public interface BannerRepository extends JpaRepository<BannerDomain, Long>{

	BannerDomain findByBannerArea(String bannerArea);
}
