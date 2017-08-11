package com.tiamaes.bike.monitor.monitor.handler;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.config.WebSocketConfig;
import com.tiamaes.security.core.userdetails.User;

@Component
public class ICMSSubscribeEvent implements ApplicationListener<SessionSubscribeEvent>{
	private static Logger logger = LogManager.getLogger(ICMSSubscribeEvent.class);
	
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperation;
	
	public void onApplicationEvent(SessionSubscribeEvent event) {
		User user = null;
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		Object simpUser = accessor.getHeader("simpUser");
		if(simpUser != null && simpUser instanceof UsernamePasswordAuthenticationToken){
			UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken)simpUser);
			user = (User)token.getPrincipal();
		}
		String destination = accessor.getDestination();
		if(StringUtils.isNotBlank(destination) && user != null){
			String username = user.getUsername();
			if(destination.startsWith(WebSocketConfig.USER_PREFIX)){
				destination = destination.substring(destination.indexOf("/", WebSocketConfig.USER_PREFIX.length() + 1));
			}
			setOperation.add(String.format(RedisKey.SUBSCRIBERS, destination), username);
			setOperation.add(String.format(RedisKey.USERTOPICS, username), destination);
			
			String id = accessor.getSubscriptionId();
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Subscribe -> [sid : %1$s, destination : '%2$s', uid : %3$s]", id, destination, username));
			}
		}
	}

}
