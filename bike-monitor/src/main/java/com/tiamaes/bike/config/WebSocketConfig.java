package com.tiamaes.bike.config;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.ExpiringSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.session.web.socket.server.SessionRepositoryMessageInterceptor;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession>{
	public static final String APP_PREFIX = "/app";
	public static final String USER_PREFIX = "/user/";
	public static final String TOPIC_PREFIX = "/topic/";
	
	@Bean
	public UserHandshakeHandler userHandshakeHandler(){
		return new UserHandshakeHandler();
	}
	
	private ObjectMapper objectMapper;
	
	@Autowired
	@Qualifier("jacksonObjectMapperBuilder")
	public void setObjectMapper(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
		ObjectMapper objectMapper = jacksonObjectMapperBuilder.createXmlMapper(false).build();
		this.objectMapper = objectMapper;
	}
	
	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		super.configureMessageConverters(messageConverters);
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		return messageConverters.add(converter);
	}
	
	@Override
	public void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socket").setHandshakeHandler(userHandshakeHandler()).addInterceptors(new HttpSessionHandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
//		registry.addEndpoint("/socket").setHandshakeHandler(userHandshakeHandler()).setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setUserDestinationPrefix(USER_PREFIX);
		registry.enableSimpleBroker(TOPIC_PREFIX, USER_PREFIX);
		registry.setApplicationDestinationPrefixes(APP_PREFIX);
	}
	
	public final class UserHandshakeHandler extends DefaultHandshakeHandler{
		@Autowired
		private SessionRepository<?> sessionRepository;
		
		@Override
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
			Assert.notNull(sessionRepository);
			String sessionId = SessionRepositoryMessageInterceptor.getSessionId(attributes);
			Session session = sessionRepository.getSession(sessionId);
			SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
			if(context != null){
				return context.getAuthentication();
			}
			
			return null;
		}
	}
}
