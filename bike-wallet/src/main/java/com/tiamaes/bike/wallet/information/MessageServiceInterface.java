package com.tiamaes.bike.wallet.information;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiamaes.bike.common.bean.JPushContent;
import com.tiamaes.bike.common.bean.Result;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 消息发送webservice远程调用存根
 * @author fyz
 *
 */
@FeignClient("bike-api")
public interface MessageServiceInterface {
	/**
	 * 发送极光推送消息
	 * @param mobile 手机号码
	 */
	@RequestMapping(value = "/api/common/message/sendJpush", method = RequestMethod.POST, produces = { "application/json" })
	@ApiOperation(value = "极光推送消息", notes = "极光推送消息")
	@ApiImplicitParam(name = "jPushContent", value = "消息实体", required = true, dataType = "JPushContent")
	public @ResponseBody Result sendJpush(@RequestBody JPushContent jPushContent);
}
