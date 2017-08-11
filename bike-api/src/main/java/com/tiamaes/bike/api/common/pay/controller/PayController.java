package com.tiamaes.bike.api.common.pay.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.api.information.authority.service.AuthorityServiceInterface;
import com.tiamaes.bike.config.SystemConfig.PayProperties;
import com.tiamaes.bike.messages.Push;
import com.tiamaes.bike.messages.SMS;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/common/pay")
@Api(tags = {"pay"}, description = "线上支付相关业务")
public class PayController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(PayController.class);
	@Resource
	private AuthorityServiceInterface authorityService;
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	@SuppressWarnings("unused")
	@Autowired
	private PayProperties payProperties;
	@Resource
	private SMS sms;
	@Resource
	private Push push;
}
