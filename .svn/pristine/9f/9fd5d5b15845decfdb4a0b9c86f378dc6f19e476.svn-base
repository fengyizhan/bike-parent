package com.tiamaes.bike.monitor.monitor.handler;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.security.core.userdetails.User;

@Component
public class ICMSDisconnectEvent implements ApplicationListener<SessionDisconnectEvent>{
	private static Logger logger = LogManager.getLogger(ICMSDisconnectEvent.class);
	
	@Resource(name = "stringRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperation;
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		User user = null;
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		Object simpUser = accessor.getHeader("simpUser");
		if(simpUser != null && simpUser instanceof UsernamePasswordAuthenticationToken){
			UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken)simpUser);
			user = (User)token.getPrincipal();
		}
		if(user != null){
			final String userName = user.getUsername();
			Set<String> topics = setOperation.members(String.format(RedisKey.USERTOPICS, userName));
			if(topics != null){
				topics.forEach(topic -> {
					setOperation.remove(String.format(RedisKey.SUBSCRIBERS, topic), userName);
				});
			}
			redisTemplate.delete(String.format(RedisKey.USERTOPICS, user.getUsername()));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Disconnect -> [sid : %1$s, uid : %2$s]", accessor.getSessionId(),user != null ? user.getUsername() : null));
		}
	}

}
