package com.tiamaes.bike.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {
	@ConfigurationProperties(prefix="jpush")
	public class JpushProperties
	{
		private String appKey;
		private String masterSecret;
		public String getAppKey() {
			return appKey;
		}
		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}
		public String getMasterSecret() {
			return masterSecret;
		}
		public void setMasterSecret(String masterSecret) {
			this.masterSecret = masterSecret;
		}
	}
	@Bean
	public JpushProperties jpushProperties()
	{
		return new JpushProperties();
	}
	
	@ConfigurationProperties(prefix="pay")
	public class PayProperties
	{
		public String getPartner() {
			return partner;
		}
		public void setPartner(String partner) {
			this.partner = partner;
		}
		public String getSeller_id() {
			return seller_id;
		}
		public void setSeller_id(String seller_id) {
			this.seller_id = seller_id;
		}
		public String getPrivate_key() {
			return private_key;
		}
		public void setPrivate_key(String private_key) {
			this.private_key = private_key;
		}
		private String partner;
		private String seller_id;
		private String private_key;
	}
	@Bean
	public PayProperties payProperties()
	{
		return new PayProperties();
	}
	
	@ConfigurationProperties(prefix="sms")
	public class SmsProperties
	{
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		private String password;
	}
	@Bean
	public SmsProperties smsProperties()
	{
		return new SmsProperties();
	}
}
