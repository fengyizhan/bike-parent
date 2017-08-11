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
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.security.core.userdetails.User;

@Component
public class ICMSUnSubscribeEvent implements ApplicationListener<SessionUnsubscribeEvent>{
	private static Logger logger = LogManager.getLogger(ICMSUnSubscribeEvent.class);
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperation;
	
	public void onApplicationEvent(SessionUnsubscribeEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String id = accessor.getSubscriptionId();
		String destination = accessor.getDestination();
		if(StringUtils.isNotBlank(id)){
			Object simpUser = accessor.getHeader("simpUser");
			if(simpUser != null && simpUser instanceof UsernamePasswordAuthenticationToken){
				UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken)simpUser);
				User user = (User)token.getPrincipal();
				final String username = user.getUsername();
				setOperation.remove(String.format(RedisKey.SUBSCRIBERS, id), username);
				setOperation.remove(String.format(RedisKey.USERTOPICS, username), id);
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Unsubscribe -> [sid : %1$s, destination : '%2$s', uid : %3$s]", id, destination, username));
				}
			}
		}
	}

}
