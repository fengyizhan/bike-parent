package com.tiamaes.bike.monitor.monitor;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SocketController {
	private static Logger logger = LogManager.getLogger(SocketController.class);
	
	@MessageMapping("/alarms")
	public void alarms(Principal principal){
		logger.debug(principal.getName());
	}
	
	@MessageMapping("/location/{id}")
	@SendTo("/topic/location/{id}")
	public void branchs(@DestinationVariable("id")String id){
	}
	
	@MessageMapping("/status/{id}")
	@SendTo("/topic/status/{id}")
	public void status(@DestinationVariable("id")String id){
	}
}
