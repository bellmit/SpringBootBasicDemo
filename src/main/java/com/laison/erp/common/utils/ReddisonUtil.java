package com.laison.erp.common.utils;

import org.redisson.api.ExecutorOptions;
import org.redisson.api.MapOptions;
import org.redisson.api.RBucket;
import org.redisson.api.RExecutorService;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
/**
 * redissonClient 有很多分布式的结合类原子类 这里只用了分布式锁
 * @author pc
 *
 */
public class ReddisonUtil {
	
	public static RedissonClient redissonClient=SpringContextUtils.getBean(RedissonClient.class);
	
	public static   RLock getRLock(String lockname) {
		return redissonClient.getLock(lockname);
	}

	public static   RReadWriteLock getRWLock(String lockname) {
		return redissonClient.getReadWriteLock(lockname);
	}
	
	public static   RLock getSysUserLock(String userId) {
		
		return redissonClient.getFairLock("Sysuser_Lock:"+userId);
	}
	
	public static <K, V>   RMap<K, V> getMap(String mapName,Class<K> keyType,Class<V> valueType) {
		MapOptions<K, V> op = MapOptions.<K, V>defaults();
		RMap<K, V> map = redissonClient.getMap(mapName,op);
		return map;
	}
	
	public static Object getEx(String executorname) {
		ExecutorOptions op = ExecutorOptions.defaults();
		//RedissonExecutorService
		
		RExecutorService executorService = redissonClient.getExecutorService("myExecutor", op);
		return executorService;
	}
	
	
	/**
	 * 
	 *	@Description:获取共享的对象
	 *  @param sharedObjectName 共享的对象的名字
	 *  @param valueType 共享的对象的 类型
	 *  @return 
	 */
	public static <V> V getSharedObject(String sharedObjectName,Class<V> valueType) {
		RBucket<V> bucket = redissonClient.getBucket(sharedObjectName);
		return bucket.get();
	}
	
	public static void setSharedObject(String sharedObjectName,Object value) {
		RBucket<Object> bucket = redissonClient.getBucket(sharedObjectName);
		bucket.set(value);
	}
	
	/**
	 *	@Description: 原子的设置
	 *  @param sharedObjectName 共享的对象的名字
	 *  @param expect
	 *  @param value
	 *  @return 设置是否成功
	 */
	public static boolean compareAndSetSharedObject(String sharedObjectName,Object expect,Object value) {
		RBucket<Object> bucket = redissonClient.getBucket(sharedObjectName);//V expect, V update
		boolean result = bucket.compareAndSet(expect,value);
		return result;
	}
	
	/**
	 * 
	 *	@Description: 清除共享对象
	 *  @param sharedObjectName 共享的对象的名字
	 */
	public static void clearSharedObject(String sharedObjectName) {
		RBucket<Object> bucket = redissonClient.getBucket(sharedObjectName);
		bucket.getAndDelete();
	}
}
