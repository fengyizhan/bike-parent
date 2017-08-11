package com.tiamaes.bike.security.captcha;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

@Configuration
public class CaptchaConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public DefaultManageableImageCaptchaService imageCaptchaService(){
		return new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(), 
				new ImageEngine(), 180, 100000 , 75000);
	}
}
