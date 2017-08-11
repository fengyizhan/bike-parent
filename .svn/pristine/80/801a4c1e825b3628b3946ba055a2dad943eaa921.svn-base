package com.tiamaes.bike.wallet;

import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.bean.wallet.WalletUser;
import com.tiamaes.bike.wallet.information.walletUser.service.WalletUserServiceInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletUserServiceTest {
	private static Logger logger = LogManager.getLogger(WalletUserServiceTest.class);
	
	private String username = "18538317749";

	@Autowired
	private WalletUserServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	@Test
	public void test001AddWalletUserTest() throws Exception {
		WalletUser walletUser = new WalletUser();
		walletUser.setUsername(username);
		walletUser.setBalance(500.0F);
		service.addWalletUser(walletUser);
		WalletUser result = service.getWalletUserByUsername(username);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test002UpdateWalletUserTest() throws Exception {
		WalletUser walletUser = new WalletUser();
		walletUser.setUsername(username);
		walletUser.setBalance(600.0F);
		service.updateWalletUser(walletUser);
		WalletUser result = service.getWalletUserByUsername(username);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test003DeleteWalletUserTest() throws Exception {
		WalletUser walletUser = service.getWalletUserByUsername(username);
		service.deleteWalletUser(walletUser);
		WalletUser result = service.getWalletUserByUsername(username);
		Assert.assertNull(result);
	}
}