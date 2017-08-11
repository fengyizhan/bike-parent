package com.tiamaes.bike.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.system.User;

/**
 * 登录成功操作
 * @author Chen
 *
 */
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		Map<String,Object> result = new HashMap<String,Object>();
		User user = (User)authentication.getPrincipal();
		result.put("state", true);
		result.put("message", user.getIndex());
		
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Expires", "0");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		objectMapper.writeValue(out, result);
	}
	
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
