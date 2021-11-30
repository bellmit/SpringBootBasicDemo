package com.laison.erp.config.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
@Log4j2
public class StopListener implements ApplicationListener<ContextClosedEvent> {


	@Autowired
	ScheduledThreadPoolExecutor ex;
	@Override
	public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
		if (contextClosedEvent.getApplicationContext().getParent() == null) {
			
			try {
				log.info("程序即将关闭 ，可以添加一些终结任务");
				ex.shutdownNow();
			} catch (Exception e) {//Mqtt
				log.info("",e);
			}
			
		}
	}
}
