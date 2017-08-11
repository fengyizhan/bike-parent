package com.tiamaes.bike.monitor.monitor.handler;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import com.tiamaes.security.core.userdetails.User;



@Component
public class ICMSConnectedEvent implements ApplicationListener<SessionConnectedEvent>{
	private static Logger logger = LogManager.getLogger(ICMSConnectedEvent.class);
	
	@Resource
	private RedisTemplate<String, ?> redisTemplate;
	
	@SuppressWarnings("unused")
	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		
		User user = null;
		Object simpUser = accessor.getHeader("simpUser");
		if(simpUser != null && simpUser instanceof UsernamePasswordAuthenticationToken){
			UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken)simpUser);
			WebAuthenticationDetails detail = (WebAuthenticationDetails)token.getDetails();
			user = (User)token.getPrincipal();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Connected -> [sid : %1$s, uid : %2$s]", accessor.getSessionId(), user != null ? user.getUsername() : null));
		}
	}

}
