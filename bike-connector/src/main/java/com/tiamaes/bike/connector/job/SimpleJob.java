package com.tiamaes.bike.connector.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tiamaes.bike.config.ChannelRepository;

@DisallowConcurrentExecution
public class SimpleJob implements Job {

    private static Logger logger = LoggerFactory.getLogger(SimpleJob.class);
    
	@Autowired
	private ChannelRepository channelRepository;

    public void execute(JobExecutionContext context)  throws JobExecutionException {
    	JobKey jobKey = context.getJobDetail().getKey();
    	if(logger.isDebugEnabled()){
			logger.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		}
    	JobDataMap matasource = context.getJobDetail().getJobDataMap();
    	String id = matasource.getString("id");
    	
    	Object response = new Object();
    	
		io.netty.channel.Channel nettyChannel = channelRepository.get(id);
		if (nettyChannel != null) {
			nettyChannel.writeAndFlush(response);
		}
    }

}
