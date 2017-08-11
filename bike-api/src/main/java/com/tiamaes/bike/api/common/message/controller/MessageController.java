package com.tiamaes.bike.api.common.message.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.api.information.authority.service.AuthorityServiceInterface;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.JPushContent;
import com.tiamaes.bike.common.bean.Result;
import com.tiamaes.bike.messages.Push;
import com.tiamaes.bike.messages.SMS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/common/message")
@Api(tags = {"message"}, description = "手机短信发送")
public class MessageController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(MessageController.class);
	@Resource
	private AuthorityServiceInterface authorityService;
	@Resource
	private RedisTemplate<String,String> redisTemplate;
	@Resource
	private SMS sms;
	@Resource
	private Push push;
	
	/**
	 * 发送手机验证码
	 * @param mobile 手机号码
	 */
	@RequestMapping(value = "/sendVerifyCode/{mobile}", method = RequestMethod.POST, produces = { "application/json" })
	@ApiOperation(value = "短信发送", notes = "短信发送")
	public @ResponseBody Result sendVerifyCode(@PathVariable("mobile") String mobile) {
		
		Result result=new Result();
		Assert.notNull(mobile,"手机号不能为空");
		//生成随机验证码
//		int number=SystemUtil.numberGenerator(6);
		int number=123456;
		//存储到session中以便登陆验证比对，5分钟失效
		String key=String.format(RedisKey.MESSAGE_VERIFYCODE,mobile);
		redisTemplate.opsForValue().set(key, String.valueOf(number),5,TimeUnit.MINUTES);
		//发送短信验证码
		String message=sms.send(mobile,"（共享单车）验证码："+number+"，5分钟内有效。");
		result.setSuccess(true);
		result.setMessage(message);
		result.getData().put("number",number);
		return result;
	}
	/**
	 * 发送极光推送消息
	 * @param mobile 手机号码
	 */
	@RequestMapping(value = "/sendJpush", method = RequestMethod.POST, produces = { "application/json" })
	@ApiOperation(value = "短信发送", notes = "短信发送")
	public @ResponseBody Result sendJpush(@RequestBody JPushContent jPushContent) {
		String mobiles=jPushContent.getMobiles();
		String content=jPushContent.getContent();
		Result result=new Result();
		Assert.notNull(mobiles,"手机号不能为空");
		Assert.notNull(content,"通知内容不能为空");
		result.setSuccess(true);
		result.setMessage(content);
		result.getData().put("body",jPushContent);
		String sendLog=push.sendMsg(content, mobiles);
		result.setMessage(sendLog);
		return result;
	}
}
