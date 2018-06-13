package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.DistrbutedData;
import com.entities.LockProcessResult;
import com.entities.LockingProcessResult;

@Service
public class ProcessLockManager {
	
	private static final String TABLE_NAME = "processInstanceLock";
	@Autowired
	private CacheService cacheService;
	
	
	public LockProcessResult lockProcess(String processId, String userId) {
		LockProcessResult lockingRs = new LockProcessResult(processId);
		DistrbutedData distrbutedData = new DistrbutedData(TABLE_NAME, processId, userId);
		
	//	cacheService.lock(distrbutedData);
		
		//Insert if not exist
		DistrbutedData existDistrbutedData = (DistrbutedData)cacheService.putIfAbsent(distrbutedData);
		
		if(existDistrbutedData == null) {
			lockingRs.setLockedByUserId(userId);
			lockingRs.setRc(LockingProcessResult.FIRST_SUCCESS);
			return lockingRs;
		}
		//check if is it same userID than insert again for reset timeToLive
		String existUserId = (String)existDistrbutedData.getData();
		
		if(userId == existUserId) {
			cacheService.set(existDistrbutedData);
			lockingRs.setLockedByUserId(userId);
			lockingRs.setRc(LockingProcessResult.CONT_SUCCESS);
			return lockingRs;
		}
		lockingRs.setLockedByUserId(existUserId);
		lockingRs.setRc(LockingProcessResult.ALREADY_LOCKED);
		//cacheService.unlock(distrbutedData);
		return lockingRs; 
		
	
	}
	
	
	public LockProcessResult unLockProcess(String processId, String userId) {
		
		LockProcessResult lockingRs = new LockProcessResult(processId);
		DistrbutedData distrbutedData = cacheService.get(TABLE_NAME, processId);
		
		
		if(distrbutedData == null) {
			lockingRs.setRc(LockingProcessResult.UNLOCKED_KEY_NOT_EXIST);
			return lockingRs;
		}
		
		String existUserId = (String)distrbutedData.getData();
		if(existUserId.equals(userId)) {
			cacheService.remove(distrbutedData.getTableName(), distrbutedData.getKey());
			lockingRs.setLockedByUserId(existUserId);
			lockingRs.setRc(LockingProcessResult.UNLOCKED_SUCCESS);
			//cacheService.unlock(distrbutedData);
			return lockingRs;
		}
		lockingRs.setLockedByUserId(existUserId);
		lockingRs.setRc(LockingProcessResult.UNLOCKED_DIFF_USER);
		return lockingRs;
	}
	
	public void lock(String processId) {
		DistrbutedData distrbutedData = new DistrbutedData(TABLE_NAME, processId, null);
		cacheService.lock(distrbutedData);
	}
	public void unlock(String processId) {
		DistrbutedData distrbutedData = new DistrbutedData(TABLE_NAME, processId, null);
		cacheService.unlock(distrbutedData);
	}
	
}
