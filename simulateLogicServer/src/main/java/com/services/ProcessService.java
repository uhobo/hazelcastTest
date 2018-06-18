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
	 
	 public ProcessInstance getProcess(String processId, String userId) {
		 
		 ProcessInstance processIntance = mongoTemplate.findById(processId, ProcessInstance.class);
		 if(processIntance == null) {
			 processIntance = new ProcessInstance();
			 processIntance.setProcessId(processId);
			 mongoTemplate.insert(processIntance);
		 }
		 
		 return processIntance; 
		 
	 }
	 
	 public void saveProcess(ProcessInstance processIntance) {
		 mongoTemplate.insert(processIntance);
	 }
	 
}
