package com.tiamaes.bike.config;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ConfigurableApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = (ConfigurableApplicationContext)applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		Assert.notNull(applicationContext, "applicaitonContext must not be empty or null");
		return applicationContext.containsBean(name) ? (T) applicationContext.getBean(name) : null;
	}

	public static <T> T getBean(Class<T> clazz) {
		Assert.notNull(applicationContext, "applicaitonContext must not be empty or null");
		Map<String,T> beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}
	
	
	public static String getString(String key){
		Assert.notNull(applicationContext, "applicaitonContext must not be empty or null");
		Assert.notNull(key, "key must not be empty or null");
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		return environment.getProperty(key);
	}
	
	public static String getString(String key, String defaultValue){
		String retVal = getString(key);
		return retVal != null ? retVal : defaultValue;
	}
	
	public static int getInt(String key){
		String retVal = getString(key);
		if(StringUtils.isNotBlank(retVal) && StringUtils.isNumeric(retVal)){
			return Integer.parseInt(retVal);
		}
		return 0;
	}
	
	public static int getInt(String key, int defaultValue){
		String value = getString(key);
		if(StringUtils.isNotBlank(value) && StringUtils.isNumeric(value)){
			return Integer.parseInt(value);
		}
		return defaultValue;
	}
}
