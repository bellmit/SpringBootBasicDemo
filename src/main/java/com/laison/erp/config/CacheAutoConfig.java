package com.laison.erp.config;

import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class CacheAutoConfig extends CachingConfigurerSupport {
	 private final static Map<String, CacheConfig> CACHE_CONFIG = new HashMap<String, CacheConfig>(); 
	 /**
	  * 过期时间 ttl =30*60*1000
	  */
	 private final static long CACHE_TTL=30*60*1000;
	 private final static long CACHE_MAX_IDLE_TIME=15*60*1000;
	 
	 /**
	  * 
	  * @Description: 添加默认配置   ttl=30分钟  idel=15分钟
	  *@param cacheName
	  *@return : void
	  */
	 public static void addDefaultCacheConfig(String cacheName) {
		 CACHE_CONFIG.put(cacheName, new CacheConfig(CACHE_TTL,CACHE_MAX_IDLE_TIME));
	 }
	 @Bean
	 CacheManager cacheManager(RedissonClient redissonClient) {
	     // 创建一个名称为" xxxx "的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
	     //CACHE_CONFIG.put(AppSingUpUtils.CACHE_NAME, new CacheConfig(10*60*1000, 10*60*1000));
	     // CACHE_CONFIG.put(SysRoleService.CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	     // CACHE_CONFIG.put(SysMenuService.CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	     // CACHE_CONFIG.put(SysDeptService.CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	     // CACHE_CONFIG.put(SysService.DEPT_TREE_CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	     // CACHE_CONFIG.put(SysService.MENU_TREE_CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	     // CACHE_CONFIG.put(SysService.ROLEMENUS_CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	     // CACHE_CONFIG.put(SysService.SUBJECT_CACHE_NAME, new CacheConfig(24*60*1000, 12*60*1000));
	      return new RedissonSpringCacheManager(redissonClient,CACHE_CONFIG,jsonJacksonCodec());
	  } 
	 
	 
	 @Bean
	 JsonJacksonCodec jsonJacksonCodec() {
	     // 创建一个名称为" xxxx "的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
	      System.out.println("new jsonJacksonCodec");
	      return new JsonJacksonCodec();
	  } 


    
}