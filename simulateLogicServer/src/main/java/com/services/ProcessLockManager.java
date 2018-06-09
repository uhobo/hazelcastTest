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
	
	
	public LockProcessResult lockProcess(String processId, Integer userId) {
		LockProcessResult lockingRs = new LockProcessResult(processId);
		DistrbutedData distrbutedData = new DistrbutedData(TABLE_NAME, processId, userId);
		//Insert if not exist
		DistrbutedData existDistrbutedData = (DistrbutedData)cacheService.putIfAbsent(distrbutedData);
		
		if(existDistrbutedData == null) {
			lockingRs.setLockedByUserId(userId);
			lockingRs.setRc(LockingProcessResult.FIRST_SUCCESS);
			return lockingRs;
		}
		//check if is it same userID than insert again for reset timeToLive
		Integer existUserId = (Integer)existDistrbutedData.getData();
		
		
		if(userId == existUserId) {
			cacheService.set(existDistrbutedData);
			lockingRs.setLockedByUserId(userId);
			lockingRs.setRc(LockingProcessResult.CONT_SUCCESS);
			return lockingRs;
		}
		lockingRs.setLockedByUserId(existUserId);
		lockingRs.setRc(LockingProcessResult.ALREADY_LOCKED);
		return lockingRs; 
	}
	
	
	public LockProcessResult unLockProcess(String processId, Integer userId) {
		
		LockProcessResult lockingRs = new LockProcessResult(processId);
		DistrbutedData distrbutedData = new DistrbutedData(TABLE_NAME, processId, userId);
		Integer existUserId = (Integer)cacheService.get(TABLE_NAME, processId);
		
		if(existUserId == null) {
			lockingRs.setRc(LockingProcessResult.UNLOCKED_KEY_NOT_EXIST);
			return lockingRs;
		}
		
		
		if(existUserId == userId) {
			cacheService.remove(distrbutedData.getTableName(), distrbutedData.getKey());
			lockingRs.setLockedByUserId(existUserId);
			lockingRs.setRc(LockingProcessResult.UNLOCKED_SUCCESS);
			return lockingRs;
		}
		lockingRs.setLockedByUserId(existUserId);
		lockingRs.setRc(LockingProcessResult.UNLOCKED_DIFF_USER);
		return lockingRs;
	}
	
	
}
