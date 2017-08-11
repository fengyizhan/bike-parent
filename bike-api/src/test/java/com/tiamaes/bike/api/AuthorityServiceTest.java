package com.tiamaes.bike.api;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.api.information.authority.service.AuthorityServiceInterface;
import com.tiamaes.bike.common.bean.system.Authority;
import com.tiamaes.security.core.DefaultGrantedAuthority;
import com.tiamaes.security.core.userdetails.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorityServiceTest {

	private static Logger logger = LogManager.getLogger(AuthorityServiceTest.class);
	
	@Autowired
	private AuthorityServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}
	
	@Test
	public void queryUserDataAuthoritiesTreeTest() throws JsonProcessingException{
		User user = setUser();
		Authority authority = service.queryUserDataAuthoritiesTree(user);
		logger.debug(jacksonObjectMapper.writeValueAsString(authority));
	}

	public static User setUser() {
		List<DefaultGrantedAuthority> authorites = new ArrayList<DefaultGrantedAuthority>();
		DefaultGrantedAuthority grantedAuthority = new DefaultGrantedAuthority("ROLE_DEVELOPER", "ROLE_DEVELOPER");
		authorites.add(grantedAuthority);
		User user = new User("18538317749", "23731d539a1ae3271657ed50820d68d49a130d35851e14733fda7155342308522bb9b3c480c54cc8",
				authorites);
		return user;
	}
}
