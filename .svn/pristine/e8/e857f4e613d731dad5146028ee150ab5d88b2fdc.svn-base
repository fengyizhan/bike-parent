package com.tiamaes.bike.api.config;

import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {
	@Resource
	private Environment environment;
	@Resource
	private ObjectMapper jacksonObjectMapper;

	@Bean(name = "com.tiamaes.ytms.config.RedisConfig.RedisClusterProperties")
	public RedisClusterProperties redisClusterProperties() {
		return new RedisClusterProperties();
	}
	
	@Bean(name = "jedisPoolConfig")
	protected JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(10);
		config.setMaxIdle(5);
		config.setMinIdle(1);
		config.setTestWhileIdle(true);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		return config;
	}

	@Bean(name = "clusterConnectionFactory")
	public JedisConnectionFactory clusterConnectionFactory() {
		RedisClusterProperties clusterProperties = redisClusterProperties();
		RedisClusterConfiguration configuration = new RedisClusterConfiguration(clusterProperties.getNodes());
		configuration.setMaxRedirects(clusterProperties.getMaxRedirects());
		JedisConnectionFactory primary = new JedisConnectionFactory(configuration, jedisPoolConfig());
		primary.setPassword(clusterProperties.getPassword());
		primary.setTimeout(clusterProperties.getTimeout());
		primary.afterPropertiesSet();
		return primary;
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, ?> clusterRedisTemplate(RedisConnectionFactory clusterConnectionFactory) throws UnknownHostException {
		RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(clusterConnectionFactory);
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		Jackson2JsonRedisSerializer<?> hashValueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		hashValueSerializer.setObjectMapper(jacksonObjectMapper);
		redisTemplate.setHashValueSerializer(hashValueSerializer);
		redisTemplate.setEnableTransactionSupport(false);
		return redisTemplate;
	}
	
	@Primary
	@Bean(name = "stringRedisTemplate")
	public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory clusterConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(clusterConnectionFactory);
		template.setEnableTransactionSupport(false);
		return template;
	}
	
	@Bean(name = "package02PipelinedRedisTemplate")
	public StringRedisTemplate package02PipelinedRedisTemplate(JedisConnectionFactory clusterConnectionFactory) {
		JedisClusterConnection connection = (JedisClusterConnection) clusterConnectionFactory.getClusterConnection();
		RedisClusterNode node = connection.clusterGetNodeForKey("{02}".getBytes());
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(new JedisShardInfo(node.getHost(), node.getPort()));
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(jedisConnectionFactory);
		template.setEnableTransactionSupport(true);
		return template;
	}

	@ConfigurationProperties(prefix = "spring.redis.cluster")
	public class RedisClusterProperties {

		private List<String> nodes;
		private String password;
		private int timeout;
		private int maxRedirects;

		public List<String> getNodes() {
			return nodes;
		}

		public void setNodes(List<String> nodes) {
			this.nodes = nodes;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getTimeout() {
			return timeout;
		}

		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}

		public int getMaxRedirects() {
			return maxRedirects;
		}

		public void setMaxRedirects(int maxRedirects) {
			this.maxRedirects = maxRedirects;
		}
	}

}
