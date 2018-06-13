package com.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "processInstance")
public class ProcessInstance implements Serializable {
	
	@Id
	private String processId;
	private Integer processType;
	private String processName;
	private Long lockingCount;
	private Long unlocingCount;
	private DistrbutedData distrbutedData;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public Integer getProcessType() {
		return processType;
	}
	public void setProcessType(Integer processType) {
		this.processType = processType;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public Long getLockingCount() {
		return lockingCount;
	}
	public void setLockingCount(Long lockingCount) {
		this.lockingCount = lockingCount;
	}
	public Long getUnlocingCount() {
		return unlocingCount;
	}
	public void setUnlocingCount(Long unlocingCount) {
		this.unlocingCount = unlocingCount;
	}
	public DistrbutedData getDistrbutedData() {
		return distrbutedData;
	}
	public void setDistrbutedData(DistrbutedData distrbutedData) {
		this.distrbutedData = distrbutedData;
	}
	
	
	
}
