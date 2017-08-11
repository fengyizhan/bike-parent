package com.tiamaes.bike;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

@EnableEurekaClient
@SpringBootApplication
@EnableRedisHttpSession
public class Application{
	private static Logger logger = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		if(logger.isDebugEnabled()){
			logger.debug("Let's inspect the beans provided by Spring Boot:\n");
			String[] array = context.getBeanDefinitionNames();
			Arrays.stream(array).forEach(bean -> logger.debug(bean));
		}
	}
	
	@Autowired
	private Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder;
	@Bean(name = "objectMapper")
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = jacksonObjectMapperBuilder.createXmlMapper(false).build();
		objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
		return objectMapper;
	}
	
}