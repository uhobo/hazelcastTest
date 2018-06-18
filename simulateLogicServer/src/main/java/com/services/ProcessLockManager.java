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
	


	public LockProcessResult lockSessionLevel(String processId, String userId) {
		DistrbutedData distrbutedData = new DistrbutedData(TABLE_NAME, processId, userId);
		LockProcessResult lockingRs = new LockProcessResult(distrbutedData.getKey());
		
		//Insert if not exist
		DistrbutedData existDistrbutedData = (DistrbutedData)cacheService.putIfAbsent(distrbutedData);
		
		if(existDistrbutedData == null) {
			lockingRs.setLockedByUserId(userId);
			lockingRs.setRc(LockingProcessResult.FIRST_SUCCESS);
			return lockingRs;
		}
		//check if is it same userID than insert again for reset timeToLive
		String existUserId = (String)existDistrbutedData.getData();
		
		if(userId.equals(existUserId)) {
			cacheService.set(existDistrbutedData);
			lockingRs.setLockedByUserId(userId);
			lockingRs.setRc(LockingProcessResult.CONT_SUCCESS);
			return lockingRs;
		}
		lockingRs.setLockedByUserId(existUserId);
		lockingRs.setRc(LockingProcessResult.ALREADY_LOCKED);
		return lockingRs;
	}
	
	
	public LockProcessResult unLockSessionLevel(String processId, String userId) {
		
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
			return lockingRs;
		}
		lockingRs.setLockedByUserId(existUserId);
		lockingRs.setRc(LockingProcessResult.UNLOCKED_DIFF_USER);
		return lockingRs;
	}
	
	public void lockServerLevel(String processId) {
		cacheService.lock(TABLE_NAME, processId);
	}
	public void unlockServerLevel(String processId) {
		cacheService.unlock(TABLE_NAME, processId);
	}
	
}
