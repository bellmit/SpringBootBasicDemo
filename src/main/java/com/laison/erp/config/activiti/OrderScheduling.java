package com.laison.erp.config.activiti;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Desc 工单定制执行器
 * @Author sdp
 * @Date 2021/5/19 13:56
 * 定时器分为 corn 秒（0-59） 分（0-59） 小时（0-23） 日（1-31） 月（1-12） 星期（1-7 或者 SUN-SAT） 年（1970-2099）
 * 每个类型中间使用逗号时，代表或
 * *代表任意
 * 0代表
 * ？可以用在日和周几的字段，代表不确定值
 */
@EnableAsync
@Configuration
public class OrderScheduling {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}
