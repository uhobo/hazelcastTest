package com.entities;

import java.io.Serializable;
import java.util.Date;

public class DistrbutedData {
	private String tableName;
	private String key;
	private Serializable data;
	private long timeToLiveMill = 2 * 60 * 1000;
	private Date insertTime;
	
	public DistrbutedData(String tableName, String key, Serializable data) {
		this.tableName = tableName;
		this.key = key;
		this.data = data;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Serializable getData() {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}
	public long getTimeToLiveMill() {
		return timeToLiveMill;
	}
	public void setTimeToLiveMill(long timeToLiveMill) {
		this.timeToLiveMill = timeToLiveMill;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
