package com.tiamaes.bike.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.connector.listener.ClientFilterStrategy;

@EnableKafka
@Configuration
public class KafkaConfig {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger();
	
	
	@ConfigurationProperties(prefix = "spring.kafka")
	public class KafkaProperties {
		private String brokers;
		private String zookeepers;

		public String getBrokers() {
			return brokers;
		}

		public void setBrokers(String brokers) {
			this.brokers = brokers;
		}

		public String getZookeepers() {
			return zookeepers;
		}

		public void setZookeepers(String zookeepers) {
			this.zookeepers = zookeepers;
		}

	}

	@Bean(name = "com.tiamaes.mms.config.KafkaConfig.KafkaProperties")
	public KafkaProperties kafkaProperties() {
		return new KafkaProperties();
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaStringTemplate() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties().getBrokers());
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties().getBrokers());
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		KafkaTemplate<String, String> template = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
		template.setMessageConverter(new StringJsonMessageConverter(objectMapper));
		return template;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() throws UnknownHostException {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties().getBrokers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, InetAddress.getLocalHost().getHostAddress());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public KafkaListenerContainerFactory<?> kafkaStringListenerContainerFactory() throws UnknownHostException {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setRecordFilterStrategy(clientFilterStrategy);
		return factory;
	}

	@Bean
	public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory() throws UnknownHostException {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
		factory.setRecordFilterStrategy(clientFilterStrategy);
		return factory;
	}
	
	@Autowired
	protected ClientFilterStrategy clientFilterStrategy;

	@Resource
	private ObjectMapper objectMapper;
}
