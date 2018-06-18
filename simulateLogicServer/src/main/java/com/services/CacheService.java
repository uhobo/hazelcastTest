package com.services;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.DistrbutedData;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Service
public class CacheService  {

	 @Autowired
	 private HazelcastInstance instance;
	 public void init() {
		Config config = new Config("resources/hazelcast.xml");
		instance = Hazelcast.newHazelcastInstance(config);
	 }
	 
	 public Object put(DistrbutedData mapData){
		 return instance.getMap(mapData.getTableName()).put(mapData.getKey(), mapData, mapData.getTimeToLiveMill(), TimeUnit.MILLISECONDS );
	 }
	 
	 public Object putIfAbsent(DistrbutedData mapData) {
		return instance.getMap(mapData.getTableName()).putIfAbsent(mapData.getKey(), mapData, mapData.getTimeToLiveMill(), TimeUnit.MILLISECONDS );
	 }
	 
	 /**
	  * The difference of set() is that it does not return the old value.
		That means a performance benefit especially if you use a mapstore.
	  * @param mapData
	  */
	 public void set(DistrbutedData mapData) {
		 instance.getMap(mapData.getTableName()).set(mapData.getKey(), mapData, mapData.getTimeToLiveMill(), TimeUnit.MILLISECONDS);
	 }
	 
	 public DistrbutedData get(String tableName, String key) {
		 return (DistrbutedData)instance.getMap(tableName).get(key);
	 }
	 
	 public DistrbutedData remove(String tableName, String key) {
		 return (DistrbutedData)instance.getMap(tableName).remove(key); 
	 }

	public void lock(String tableName, String key) {
		instance.getMap(tableName).lock(key);
		
	}
	
	public void unlock(String tableName, String key) {
		
		instance.getMap(tableName).unlock(key);
		
	}
	 
}
