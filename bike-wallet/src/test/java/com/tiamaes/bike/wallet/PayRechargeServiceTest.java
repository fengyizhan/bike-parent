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
import com.tiamaes.bike.common.bean.pay.PayRecharge;
import com.tiamaes.bike.common.bean.pay.PayRecharge.MoneyType;
import com.tiamaes.bike.common.bean.pay.PayRecharge.Style;
import com.tiamaes.bike.wallet.information.pay.service.PayRechargeServiceInterface;
import com.tiamaes.mybatis.Pagination;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PayRechargeServiceTest {
	private static Logger logger = LogManager.getLogger(PayRechargeServiceTest.class);
	
	private String username = "18538317749";
	
	private Integer id = 10000;

	@Autowired
	private PayRechargeServiceInterface service;
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;

	@Before
	public void before() {
		assertNotNull("Service not found...", service);
		assertNotNull("jacksonObjectMapper not found...", jacksonObjectMapper);
	}

	@Test
	public void test001AddPayRechargeTest() throws Exception {
		/**
		 * 交押金
		 */
		PayRecharge payRecharge = new PayRecharge();
		payRecharge.setId(id);
		payRecharge.setUsername(username);
		payRecharge.setMoney(100.0F);
		payRecharge.setMoneyType(MoneyType.DEPOSIT);
		payRecharge.setStyle(Style.ALIPAY);
		payRecharge.setCreateTime(new Date());
		payRecharge.setResultCode("FINISHED");
		service.addPayRecharge(payRecharge);
		PayRecharge result = service.getPayRechargeById(id);
		Assert.assertNotNull(result);
		logger.debug("押金："+jacksonObjectMapper.writeValueAsString(result));
		/**
		 * 充值
		 */
		PayRecharge payRecharge2 = new PayRecharge();
		payRecharge.setId(service.getId());
		payRecharge.setUsername(username);
		payRecharge.setMoney(20.0F);
		payRecharge.setMoneyType(MoneyType.RECHARGE);
		payRecharge.setStyle(Style.ALIPAY);
		payRecharge.setCreateTime(new Date());
		payRecharge.setResultCode("FINISHED");
		service.addPayRecharge(payRecharge2);
		PayRecharge result2 = service.getPayRechargeById(payRecharge.getId());
		Assert.assertNotNull(result2);
		logger.debug("充值："+jacksonObjectMapper.writeValueAsString(result2));
	}
	
	@Test
	public void test002GetListOfPayRechargesByUsername() throws Exception {
		PayRecharge recharge = new PayRecharge();
		recharge.setUsername(username);
		Pagination<PayRecharge> pagination = new Pagination<>(1, 10);
		List<PayRecharge> recharges = service.getListOfPayRechargesByUsername(recharge, pagination);
		Assert.assertNotNull(recharges);
		logger.debug(jacksonObjectMapper.writeValueAsString(recharges));
	}
	
	@Test
	public void test003UpdatePayRechargeTest() throws Exception {
		PayRecharge recharge = new PayRecharge();
		recharge.setUsername(username);
		recharge.setId(id);
		recharge.setUsername(username);
		recharge.setMoney(500.0F);
		recharge.setStyle(Style.ALIPAY);
		service.updatePayRecharge(recharge);
		PayRecharge result = service.getPayRechargeById(id);
		Assert.assertNotNull(result);
		logger.debug(jacksonObjectMapper.writeValueAsString(result));
	}
	
}