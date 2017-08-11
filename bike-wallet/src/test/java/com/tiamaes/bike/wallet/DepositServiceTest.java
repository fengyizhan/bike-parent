package com.tiamaes.bike.wallet;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

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
import com.tiamaes.bike.common.bean.wallet.Deposit;
import com.tiamaes.bike.common.bean.wallet.Deposit.Type;
import com.tiamaes.bike.common.bean.wallet.Style;
import com.tiamaes.bike.wallet.information.deposit.service.DepositServiceInterface;
import com.tiamaes.mybatis.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepositServiceTest {
	private static Logger logger = LogManager.getLogger(DepositServiceTest.class);
	
	private String username = "18538317749";
	
	private Integer id = 10000;

	@Autowired
	private DepositServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	@Test
	public void test001AddDepositTest() throws Exception {
		Deposit deposit = new Deposit();
		deposit.setId(id);
		deposit.setUsername(username);
		deposit.setDeposit(300.0F);
		deposit.setStyle(Style.ALIPAY);
		deposit.setType(Type.RECHARGE);
		deposit.setCreateDate(new Date());
		service.addDeposit(deposit);
		Deposit result = service.getDepositById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
	@Test
	public void test002GetListOfDeposits() throws Exception {
		Deposit deposit = new Deposit();
		Pagination<Deposit> pagination = new Pagination<>(1, 20);
		List<Deposit> deposits = service.getListOfDeposits(deposit, pagination);
		Assert.assertNotNull(deposits);
		logger.debug(jacksonObjectMapper.writeValueAsString(deposits));
	}
	
	@Test
	public void test003UpdateDepositTest() throws Exception {
		Deposit deposit = new Deposit();
		deposit.setUsername(username);
		deposit.setId(id);
		deposit.setUsername(username);
		deposit.setDeposit(300.0F);
		deposit.setStyle(Style.ALIPAY);
		deposit.setType(Type.REFUND);
		service.updateDeposit(deposit);
		Deposit result = service.getDepositById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
}