package com.tiamaes.bike.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

//@Configuration
//@EnableScheduling
//@EnableAutoConfiguration
public class SchedulerConfig implements SchedulingConfigurer {

	@Resource(name="dataSource")
	protected DataSource dataSource;

	@Bean
	public SpringBeanJobFactory springBeanJobFactory() {
		return new SpringBeanJobFactory();
	}

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(1000);
		return executor;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(springBeanJobFactory());
		factory.setTaskExecutor(threadPoolTaskExecutor());
		Properties quartzProperties = new Properties();
		quartzProperties.put("org.quartz.scheduler.rmi.export", true);
		quartzProperties.put("org.quartz.scheduler.rmi.registryHost", "localhost");
		quartzProperties.put("org.quartz.scheduler.rmi.registryPort", "1099");
		quartzProperties.put("org.quartz.scheduler.rmi.createRegistry", true);
		quartzProperties.put("org.quartz.scheduler.skipUpdateCheck", true);
		factory.setQuartzProperties(quartzProperties);
		factory.setDataSource(dataSource);
		return factory;
	}
	
	@Bean(name = "taskScheduler")
	public ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(5);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		return scheduler;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(taskScheduler());
	}
}
