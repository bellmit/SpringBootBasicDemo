package com.laison.erp.common.utils;

import java.util.Date;

import com.laison.erp.dao.sys.SequenceRecordDao;
import com.laison.erp.model.sys.SequenceRecord;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;


import cn.hutool.core.date.DateUtil;

//import com.laison.nlapis.service.sys.SequenceRecordService;

public class SequenceUtil {
	
	public static final int AUTO_INCREMENT_TOKEN = 9;
	public static Integer TRADE_ID_TYPE=10;
	public static Integer INVOICE_TYPE=11;
	
	public static Integer CUSTOOMER_INFO=12;
	public static Integer TA_CUSTOOMER_INFO=13;
	public static Integer TA_BACK_CUSTOOMER_INFO=14;
	public static Integer COMP_ID=1;
	
	
	
	/**
	 * 
	 * @Title: generateCode  
	 * @Description: 产生code 并直接更新 带锁产生的code不会重复  
	 * @param codeType
	 * @return long
	 * @throws Exception 
	 */
	public static long generateSequenceCode(int codeType,int startvalue) throws Exception  {
		
		RedissonClient redissonClient=SpringContextUtils.getBean(RedissonClient.class);
		RLock lock= redissonClient.getLock("laison_lock_"+codeType);
		try {
			lock.lock() ;
			return getCode(codeType,startvalue);
		}  finally {
			lock.unlock();
		}	
	}
	
	public static String generateautoIncrementToken(int customerid) throws Exception  {
		
		RedissonClient redissonClient=SpringContextUtils.getBean(RedissonClient.class);
		RLock lock= redissonClient.getLock("laison_lock_"+customerid);
		try {
			lock.lock() ;
			return getCode("token"+customerid,1)+"";
		}  finally {
			lock.unlock();
		}	
	}
	
	
	
	/**
	 * 产生客户主键
	 * @return
	 * @throws Exception
	 */
	public static int getCustomerId() throws Exception{
		int customerIdGenType = LoginUserUtil.getSysUser().getSysDept().getConfig().getCustomerIdGenType();
		if(customerIdGenType == 2) {
			return (int) generateSequenceCode(TA_CUSTOOMER_INFO,1);
		}else if(customerIdGenType == 3) {
			return (int) generateSequenceCode(TA_BACK_CUSTOOMER_INFO,1);
		}else {
			return (int) generateSequenceCode(CUSTOOMER_INFO,10001);
		}
	}
	
	public static int getCustomerId(int customerIdGenType) throws Exception{
		if(customerIdGenType == 2) {
			String idstring = generateSequenceCode(TA_CUSTOOMER_INFO,1)+"";
			return Integer.parseInt(idstring.substring(2)) ;
		}else if(customerIdGenType == 3) {
			return (int) generateSequenceCode(TA_BACK_CUSTOOMER_INFO,100000001);
		}else {
			return (int) generateSequenceCode(CUSTOOMER_INFO,10001);
		}
	}
	public static int getCompId() throws Exception{
		return (int) generateSequenceCode(COMP_ID,1);
	}
	/**
	 * 
	 * @return  产生发票
	 * @throws Exception
	 */
	public static String generateInvoice() throws Exception{
		Date utcDate = DateUtils.utcDate();
		String prefix = DateUtils.formatDate(utcDate, "yyMMddHH");
		prefix+=INVOICE_TYPE;
		int codeType = Integer.parseInt(prefix);
		return prefix+generateSequenceCode(codeType,100001)+"";
	}
	
	public static String  generateTradeId() throws Exception {
		Date utcDate = DateUtils.utcDate();
		String prefix = DateUtils.formatDate(utcDate, "yyMMddHH");
		prefix+=TRADE_ID_TYPE;
		int codeType = Integer.parseInt(prefix);
		return prefix+generateSequenceCode(codeType,100001);
	}
	private static Integer getCode(int codeType,int startvalue) throws Exception {	
		//SequenceRecordService	sequenceRecordService= SpringContextUtils.getBean(SequenceRecordService.class);
		
		String codePrefix = "" + codeType;
		SequenceRecord sequenceRecord = getSequenceRecord(codePrefix,startvalue);
		Integer currentNo = sequenceRecord.getSequenceNo();
		sequenceRecord.addCurrentNo();
		SpringContextUtils.getBean(SequenceRecordDao.class).updateByPrimaryKeySelective(sequenceRecord);
		Cache cache = SpringContextUtils.getCache("SequenceRecord");
		cache.put(codePrefix, sequenceRecord);
		return currentNo;
		
	}
	private static Integer getCode(String codePrefix,int startvalue) throws Exception {	
		//SequenceRecordService	sequenceRecordService= SpringContextUtils.getBean(SequenceRecordService.class);
		SequenceRecord sequenceRecord = getSequenceRecord(codePrefix,startvalue);
		Integer currentNo = sequenceRecord.getSequenceNo();
		sequenceRecord.addCurrentNo();
		SpringContextUtils.getBean(SequenceRecordDao.class).updateByPrimaryKeySelective(sequenceRecord);
		Cache cache = SpringContextUtils.getCache("SequenceRecord");
		cache.put(codePrefix, sequenceRecord);
		return currentNo;
		
	}
	private static SequenceRecord getSequenceRecord(String codePrefix, int startvalue) {
		Cache cache = SpringContextUtils.getCache("SequenceRecord");
		ValueWrapper valueWrapper = cache.get(codePrefix);
		SequenceRecord sequenceRecord;
		if (valueWrapper == null || valueWrapper.get() == null) {// 缓存中没有 从数据库获取
			sequenceRecord=SpringContextUtils.getBean(SequenceRecordDao.class).selectByCodePrefix(codePrefix);
			if (sequenceRecord == null) {// 如果没查到
				sequenceRecord = new SequenceRecord();
				sequenceRecord.setSequenceNo(startvalue);
				sequenceRecord.setSequencePrefix(codePrefix);
				SpringContextUtils.getBean(SequenceRecordDao.class).insert(sequenceRecord);// 插入到数据库
			}	
		}else {
			sequenceRecord=(SequenceRecord) valueWrapper.get();
		}
		return sequenceRecord;
	}
	
}
