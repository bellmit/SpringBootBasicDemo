package com.laison.erp.config.listener;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laison.erp.common.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@Log4j2
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {


	@Autowired
	RedissonClient redissonClient;
	
	
	

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	DataSource dataSource;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new JsonUtils.I18NStringSerializer());//翻译字符串
        //module.addSerializer(Long.class, ToStringSerializer.instance);
        //module.addSerializer(Long.TYPE, ToStringSerializer.instance);//解决long精度丢失问题
        objectMapper.registerModule(module);
		
	}
}
