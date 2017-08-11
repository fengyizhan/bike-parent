package com.tiamaes.bike.connector;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

@Component
public class TCPServer {
	private static Logger logger = LogManager.getLogger(TCPServer.class);
    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress inetSocketAddress;

    private Channel serverChannel;

    @PostConstruct
    public void start() throws Exception {
    	if(logger.isDebugEnabled()){
    		logger.debug("ServerChannel is starting...");
    	}
    	serverChannel = serverBootstrap.bind(inetSocketAddress).sync().channel();
//    	serverChannel.closeFuture().sync();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannel.close();
        serverChannel.parent().close();
        if(logger.isDebugEnabled()){
    		logger.debug("ServerChannel has stoped.");
    	}
    }
}
