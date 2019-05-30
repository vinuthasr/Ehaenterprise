package com.elephant.domain.uploadproduct;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bulkProduct")
public class BulkProduct implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7702889674950341059L;
	
	@Id
	@Column(name="bulkProductId")
	private String bulkProductId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "size")
	private long size;
	
	@Column(name ="path")
	private String path;
	
	@Column(name = "modifiedDate")
	private Date modifiedDate;
	
	@Column(name = "createdDate")
	private Date createdDate;
	
	@Column(name = "userId")
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBulkProductId() {
		return bulkProductId;
	}
	public void setBulkProductId(String bulkProductId) {
		this.bulkProductId = bulkProductId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
