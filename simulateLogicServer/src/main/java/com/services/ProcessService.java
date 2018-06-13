package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.entities.LockProcessResult;
import com.entities.LockingProcessResult;
import com.entities.ProcessInstance;

@Service
public class ProcessService {
	
	
	 @Autowired
	 MongoTemplate mongoTemplate;
	 
	 @Autowired
	 ProcessLockManager lockManager;
	// private final static String TABLE_NAME  = "processInstance";
	 
	 public ProcessInstance getProcess(String processId, String userId) {
		 
		 LockProcessResult lockResult = lockManager.lockProcess(processId, userId);
		 if(lockResult.getRc().equals(LockingProcessResult.ALREADY_LOCKED)) {
			 return null;
		 }
		 
		 ProcessInstance processIntance = mongoTemplate.findById(processId, ProcessInstance.class);
		 if(processIntance == null) {
			 processIntance = new ProcessInstance();
			 processIntance.setProcessId(processId);
			 processIntance.setLockingCount(Long.valueOf(1));
			 mongoTemplate.insert(processIntance);
		 }else {
			 processIntance.setLockingCount(processIntance.getLockingCount()+ 1);
		 }
		 
		 return processIntance; 
		 
		 
	 }
	 
	 
}
