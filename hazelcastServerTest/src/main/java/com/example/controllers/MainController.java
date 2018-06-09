package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.MapData;
import com.example.services.CacheService;

@RestController
public class MainController {
	@Autowired
	CacheService cacheService;

	public Object putIfAbsent(MapData mapData) {
		return cacheService.putIfAbsent(mapData);
	}
	
	public Object put(MapData mapData) {
		return cacheService.put(mapData);
	}
	
	public void set(MapData mapData) {
		cacheService.set(mapData);
	}
	
	public Object remove(String tableName, String key) {
		return cacheService.remove(tableName, key);
	}
	
}
