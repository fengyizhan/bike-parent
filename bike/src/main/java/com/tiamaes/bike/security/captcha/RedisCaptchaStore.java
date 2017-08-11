package com.tiamaes.bike.security.captcha;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.util.Assert;

import com.octo.captcha.Captcha;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaAndLocale;
import com.octo.captcha.service.captchastore.MapCaptchaStore;
import com.tiamaes.bike.common.RedisKey;

public class RedisCaptchaStore extends MapCaptchaStore {
	
	@Resource
	private RedisTemplate<String,CaptchaAndLocale> redisTemplate;
	private HashOperations<String, String, CaptchaAndLocale> hashOperations;
	
	public RedisCaptchaStore(RedisTemplate<String,CaptchaAndLocale> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		this.hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void cleanAndShutdown() {
		empty();
	}

	@Override
	public void empty() {
		redisTemplate.delete(RedisKey.CAPTASTORE);
	}

	@Override
	public Captcha getCaptcha(String id) throws CaptchaServiceException {
		Assert.notNull(id, "parameter 'id' must not be empty or null");
		CaptchaAndLocale captchaAndLocale = hashOperations.get(RedisKey.CAPTASTORE, id);
		return captchaAndLocale!=null ? captchaAndLocale.getCaptcha():null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Collection getKeys() {
		return hashOperations.keys(RedisKey.CAPTASTORE);
	}

	@Override
	public Locale getLocale(String id) throws CaptchaServiceException {
		Assert.notNull(id, "parameter 'id' must not be empty or null");
		CaptchaAndLocale captchaAndLocale = hashOperations.get(RedisKey.CAPTASTORE, id);
		return captchaAndLocale != null ? captchaAndLocale.getLocale() : null;
	}

	@Override
	public int getSize() {
		Set<?> store = hashOperations.keys(RedisKey.CAPTASTORE);
		return store != null ? store.size() : 0;
	}

	@Override
	public boolean hasCaptcha(String id) {
		Assert.notNull(id, "parameter 'id' must not be empty or null");
		return hashOperations.hasKey(RedisKey.CAPTASTORE, id);
	}

	@Override
	public void initAndStart() {
	}

	@Override
	public boolean removeCaptcha(String id) {
		Assert.notNull(id, "parameter 'id' must not be empty or null");
		hashOperations.delete(RedisKey.CAPTASTORE, id);
		return true;
	}

	@Override
	public void storeCaptcha(String id, Captcha captcha) throws CaptchaServiceException {
		Assert.notNull(id, "parameter 'id' must not be empty or null");
		Assert.notNull(captcha, "parameter 'captcha' must not be empty or null");
		hashOperations.put(RedisKey.CAPTASTORE, id, new CaptchaAndLocale(captcha));
	}

	@Override
	public void storeCaptcha(String id, Captcha captcha, Locale locale) throws CaptchaServiceException {
		Assert.notNull(id, "parameter 'id' must not be empty or null");
		Assert.notNull(captcha, "parameter 'captcha' must not be empty or null");
		Assert.notNull(captcha, "parameter 'locale' must not be empty or null");
		hashOperations.put(RedisKey.CAPTASTORE, id, new CaptchaAndLocale(captcha, locale));
	}
	

}
