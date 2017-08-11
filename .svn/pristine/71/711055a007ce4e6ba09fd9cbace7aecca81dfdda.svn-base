package com.tiamaes.bike.config;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tiamaes.bike.connector.handler.SomethingChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@Configuration
public class NettyConfiguration {

	@ConfigurationProperties(prefix = "spring.netty")
	public class NettyProperties {
		private int tcpPort;
		private int bossThreadCount;
		private int workerThreadCount;
		private boolean soKeepalive;
		private int soBacklog;

		public int getTcpPort() {
			return tcpPort;
		}

		public void setTcpPort(int tcpPort) {
			this.tcpPort = tcpPort;
		}

		public int getBossThreadCount() {
			return bossThreadCount;
		}

		public void setBossThreadCount(int bossThreadCount) {
			this.bossThreadCount = bossThreadCount;
		}

		public int getWorkerThreadCount() {
			return workerThreadCount;
		}

		public void setWorkerThreadCount(int workerThreadCount) {
			this.workerThreadCount = workerThreadCount;
		}

		public boolean isSoKeepalive() {
			return soKeepalive;
		}

		public void setSoKeepalive(boolean soKeepalive) {
			this.soKeepalive = soKeepalive;
		}

		public int getSoBacklog() {
			return soBacklog;
		}

		public void setSoBacklog(int soBacklog) {
			this.soBacklog = soBacklog;
		}

	}
	
	
	@Bean(name = "com.tiamaes.mms.config.NettyConfiguration.NettyProperties")
	public NettyProperties nettyProperties() {
		return new NettyProperties();
	}
	
	@Bean(name = "serverBootstrap")
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public ServerBootstrap bootstrap(NettyProperties nettyProperties) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup(nettyProperties), workerGroup(nettyProperties))
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(somethingChannelInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions(nettyProperties);
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (ChannelOption option : keySet) {
        	serverBootstrap.option(option, tcpChannelOptions.get(option));
        }
        return serverBootstrap;
    }

    @Autowired
    @Qualifier("somethingChannelInitializer")
    private SomethingChannelInitializer somethingChannelInitializer;

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions(NettyProperties nettyProperties) {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, nettyProperties.isSoKeepalive());
        options.put(ChannelOption.SO_BACKLOG, nettyProperties.getSoBacklog());
        return options;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup(NettyProperties nettyProperties) {
        return new NioEventLoopGroup(nettyProperties.getBossThreadCount());
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup(NettyProperties nettyProperties) {
        return new NioEventLoopGroup(nettyProperties.getWorkerThreadCount());
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort(NettyProperties nettyProperties) {
        return new InetSocketAddress(nettyProperties.getTcpPort());
    }
}
