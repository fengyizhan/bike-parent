package com.tiamaes.bike.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.security.execption.InvalidVerifierException;
import com.tiamaes.security.execption.NullVerifierException;

/**
 * 登录失败操作
 * 错误码：
 * 		1000 密码错误
 * 		1001 重复登录错误
 * 		1002 用户名错误
 * @author Chen
 *
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	private ObjectMapper objectMapper;
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String error = null;
		if(exception instanceof SessionAuthenticationException){
			error = "用户已经登录，限制二次登录";
		}else if(exception instanceof BadCredentialsException){
			error = "账号或密码错误";
		}else if(exception instanceof NullVerifierException){
			error = "验证码不能为空";
		}else if(exception instanceof InvalidVerifierException ){
			error = exception.getMessage();
		}else if(exception instanceof AccountExpiredException){
			error = "账号已过期";
		}else if(exception instanceof LockedException){
			error = exception.getMessage();
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("state", false);
		result.put("message", error);
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		objectMapper.writeValue(out, result);
	}

}
