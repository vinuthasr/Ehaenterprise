package com.elephant.model.uploadproduct;

import java.util.Date;


public class BulkProductModel{

	private String bulkProductId;
	private String name;
	private String type;
	private long size;
	private String path;
	private Date modifiedDate;
	private Date createdDate;
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
