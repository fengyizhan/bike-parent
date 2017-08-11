package com.tiamaes.bike.api.authority.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.provisioning.MutableUser;
import com.tiamaes.bike.api.authority.user.service.UserServiceInterface;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.system.UserPasswd;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user/center")
@Api(tags = {"userCenter"}, description = "用户中心")
public class UserCenterController {
	@Resource
	private UserServiceInterface userService;
	@Resource
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/updatepwd", method = RequestMethod.PUT)
	@ApiOperation(value = "更改用户密码", notes = "更改当前用户的密码")
	@ApiImplicitParam(name = "user", value = "用户密码实体", required = true, dataType = "UserPasswd")
	public @ResponseBody Object update(@RequestBody UserPasswd user, @CurrentUser com.tiamaes.security.core.userdetails.User operator) {
		Map<String, Object> result = new HashMap<String, Object>();
		User actual = userService.getUserById(operator.getUsername());
		boolean check = passwordEncoder.matches(user.getOldpasswd(), actual.getPassword());
		if(check){
			MutableUser mutableUser = new MutableUser(operator);
			mutableUser.setPassword(passwordEncoder.encode(user.getNewpasswd()));
			userService.upadatePassword(mutableUser);
			result.put("state", true);
			result.put("message", "密码修改完成，下次登录请使用新密码");
		}else{
			result.put("state", false);
			result.put("message", "原密码不正确");
		}
		return result;
	}
}
