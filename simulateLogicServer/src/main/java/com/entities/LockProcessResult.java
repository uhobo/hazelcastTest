package com.entities;

public class LockProcessResult {
	
	private String processId;
	private String lockedByUserId;
	
	private LockingProcessResult rc;

	public LockProcessResult(String processId) {
		this.processId = processId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	

	public String getLockedByUserId() {
		return lockedByUserId;
	}

	public void setLockedByUserId(String lockedByUserId) {
		this.lockedByUserId = lockedByUserId;
	}

	public LockingProcessResult getRc() {
		return rc;
	}

	public void setRc(LockingProcessResult rc) {
		this.rc = rc;
	}
	
	
}
