package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.LockProcessResult;
import com.entities.ProcessInstance;
import com.services.ProcessLockManager;
import com.services.ProcessService;


@RestController
@RequestMapping(path = "/rest")
public class MainController {
	@Autowired
	ProcessService processService;
	
	@Autowired
	ProcessLockManager processLockManager;
	
	 @RequestMapping("/hello")
	public String hello() {
		 getProcess("1234", "1234");
		 
		 
		 return "hello world";
	}
	 
	 
	 
	 @RequestMapping("/getProcess")
	public LockProcessResult getProcess(@RequestParam(value="processId") String processId, @RequestParam(value="userId") String userId) {
		 
		 //ProcessInstance processInstance = processService.getProcess(processId, userId);
		 try {
			 processLockManager.lockServerLevel(processId);
			 LockProcessResult lockProcessResult = processLockManager.lockSessionLevel(processId, userId);
			Thread.sleep(2000);
			System.out.println(String.format("UserId %d : Locking proccess %s result %s lockBy %d", userId, processId, lockProcessResult.getRc(), lockProcessResult.getLockedByUserId() ));
			return lockProcessResult;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			processLockManager.unlockServerLevel(processId);
			
		}
		return null;
	}
	 @RequestMapping("/releaseProcess")
	public LockProcessResult releaseProcess(@RequestParam(value="processId") String processId, @RequestParam(value="userId") String userId) {
		 
		 try {
			 processLockManager.lockServerLevel(processId);
			 LockProcessResult lockProcessResult =  processLockManager.unLockSessionLevel(processId, userId);
			 System.out.println(String.format("UserId %d : Locking proccess %s result %s lockBy %d", userId, processId, lockProcessResult.getRc(), lockProcessResult.getLockedByUserId() ));
			 return lockProcessResult;
		 }finally {
			 processLockManager.unlockServerLevel(processId);
		 }
		 
	}
	
}
