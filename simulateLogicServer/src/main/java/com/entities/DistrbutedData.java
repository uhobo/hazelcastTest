package com.entities;

import java.io.Serializable;
import java.util.Date;

public class DistrbutedData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tableName;
	private String key;
	private Serializable data;
	private long timeToLiveMill = 2 * 60 * 1000; //2 minutes
	private Date insertTime;
	private boolean lockEntry;
	private long timeToLovck = 10 * 1000; //10 seconds
	
	
	
	
	public long getTimeToLovck() {
		return timeToLovck;
	}


	public void setTimeToLovck(long timeToLovck) {
		this.timeToLovck = timeToLovck;
	}


	public boolean isLockEntry() {
		return lockEntry;
	}


	public void setLockEntry(boolean lockEntry) {
		this.lockEntry = lockEntry;
	}


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
