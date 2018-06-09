package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;

//@Configuration("hazelcast")
public class HazelcastConfiguration {

	
	//@Bean
	 public Config config() {
		
		Config config =  new Config().addMapConfig( 
			   new MapConfig()
			    .setName("accepted-messages")
			    .setEvictionPolicy(EvictionPolicy.LRU)
			    .setTimeToLiveSeconds(2400))
			  .setProperty("hazelcast.logging.type","slf4j");
		
		config.getNetworkConfig().setPort(5600).setPortAutoIncrement(false);
		
		
		return config;
	 }
	
}
