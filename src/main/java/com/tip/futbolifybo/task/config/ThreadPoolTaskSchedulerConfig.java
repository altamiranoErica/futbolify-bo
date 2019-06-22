package com.tip.futbolifybo.task.config;

import com.tip.futbolifybo.task.PollTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ComponentScan(basePackages="com.tip.futbolifybo.task")
public class ThreadPoolTaskSchedulerConfig {

//    @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
//        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//        threadPoolTaskScheduler.setPoolSize(5);
//        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
//        return threadPoolTaskScheduler;
//    }
}
