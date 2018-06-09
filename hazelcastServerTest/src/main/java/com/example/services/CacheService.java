package com.example.services;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.MapData;
import com.hazelcast.core.HazelcastInstance;

@Service
public class CacheService  {

	 @Autowired
	 private HazelcastInstance instance;
	 
	 
	 
	 
	 public Object put(MapData mapData){
		 return instance.getMap(mapData.getTableName()).put(mapData.getKey(), mapData.getData(), mapData.getTimeToLiveMill(), TimeUnit.MILLISECONDS );
	 }
	 
	 public Object putIfAbsent(MapData mapData) {
		return instance.getMap(mapData.getTableName()).putIfAbsent(mapData.getKey(), mapData.getData(), mapData.getTimeToLiveMill(), TimeUnit.MILLISECONDS );
	 }
	 
	 
	 public void set(MapData mapData) {
		 instance.getMap(mapData.getTableName()).set(mapData.getKey(), mapData.getData(), mapData.getTimeToLiveMill(), TimeUnit.MILLISECONDS);
	 }
	 
	 public Object get(MapData mapData) {
		 return instance.getMap(mapData.getTableName()).get(mapData.getKey());
	 }
	 
	 public Object remove(String tableName, String key) {
		 return instance.getMap(tableName).remove(key); 
	 }
	 
}
