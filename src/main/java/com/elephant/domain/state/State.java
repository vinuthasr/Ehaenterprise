package com.elephant.domain.state;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.elephant.domain.city.CityDomain;

@Entity
@Table(name="state")
public class State implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2837436206071338244L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stateId")
	private long stateId;
	
	@Column(name="stateName")
	private String stateName;

	@Column(name="stateCode")
	private String stateCode;
	
	
//	@OneToMany(mappedBy="city", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY )
//	private List<CityDomain> cityDomain;
	
	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

//	public List<CityDomain> getCityDomain() {
//		return cityDomain;
//	}
//
//	public void setCityDomain(List<CityDomain> cityDomain) {
//		this.cityDomain = cityDomain;
//	}
	
}
