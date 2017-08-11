package com.tiamaes.bike.config;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.information.Vehicle;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(RedisConfig.class);
	
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
	
	@Resource
	private Environment environment;
	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper objectMapper;

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

	@Primary
	@Bean(name = "redisTemplate")
	public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory clusterConnectionFactory){
		RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(clusterConnectionFactory);
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
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
	
	@Bean(name = "jsonRedisTemplate")
	public RedisTemplate<String, ?> jsonRedisTemplate(RedisConnectionFactory clusterConnectionFactory){
		RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(clusterConnectionFactory);
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		redisTemplate.setEnableTransactionSupport(false);
		return redisTemplate;
	}
	@Bean
	public DefaultRedisScript<Vehicle> vehicleScriptExecutor(){
		DefaultRedisScript<Vehicle> redisScript = new DefaultRedisScript<Vehicle>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/vehicle.lua")));
		redisScript.setResultType(Vehicle.class);
		return redisScript;
	}
	
	@Bean(name = "vehicleTemplate")
	public RedisTemplate<String, Vehicle> vehicleTemplate(RedisConnectionFactory clusterConnectionFactory){
		JedisClusterConnection connection = (JedisClusterConnection) clusterConnectionFactory.getClusterConnection();
		RedisClusterNode node = connection.clusterGetNodeForKey("{vehicles}".getBytes());
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(new JedisShardInfo(node.getHost(), node.getPort()));
		RedisTemplate<String, Vehicle> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		redisTemplate.setEnableTransactionSupport(false);
		return redisTemplate;
	}
}
