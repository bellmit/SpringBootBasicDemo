package com.laison.erp.config;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import com.laison.erp.common.utils.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;



/**
 * 定时任务配置
 * 
 * @author pc
 *
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

	

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutors());
	}

	@Bean(destroyMethod = "shutdown")
	public ScheduledThreadPoolExecutor taskExecutors() {
		ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(50);
		ex.setRemoveOnCancelPolicy(true);// 取消后就从线程池移除，没有这句的话只是取消任务
		ex.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return ex;
	}

	@Scheduled(cron = "0 0 2 * * ?") // 每天2点产生后付费账单
	public void queryTradeRecord() throws Exception {
		

	}
	
	@Scheduled(cron = "0 20 0 * * ?") // 每天2点
	public void updateCustomerPrice() throws Exception {

	}

	@Scheduled(cron = "0/10 * * * * ?") // 每隔10秒清理一下首页统计
	public void clearIndexData() throws Exception {
	}

}