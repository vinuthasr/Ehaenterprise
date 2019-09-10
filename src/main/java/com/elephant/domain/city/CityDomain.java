package com.elephant.domain.city;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.elephant.domain.state.State;

@Entity
@Table(name="city")
public class CityDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4324999829375534666L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cityId")
	private long cityId;
	
	@Column(name="cityName")
	private String cityName;
	
//	@ManyToOne
//	@JoinColumn(name="stateId")
//	private State stateDomain;

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

//	public State getStateDomain() {
//		return stateDomain;
//	}
//
//	public void setStateDomain(State stateDomain) {
//		this.stateDomain = stateDomain;
//	}
}
