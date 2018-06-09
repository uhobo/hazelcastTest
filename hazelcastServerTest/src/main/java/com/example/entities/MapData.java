package com.example.entities;

import java.io.Serializable;
import java.util.Date;

public class MapData {
	private String tableName;
	private String key;
	private Serializable data;
	private long timeToLiveMill;
	private Date insertTime;
	
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
