package com.tiamaes.bike.wallet.information.pay.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.pay.PayRecharge;
import com.tiamaes.bike.wallet.information.pay.persistence.PayRechargeMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PayRechargeService implements PayRechargeServiceInterface {
	
	@Resource
	private PayRechargeMapper payRechargeMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PayRecharge getPayRechargeById(int id) {
		Assert.notNull(id, "充值记录编号不能为空!");
		PayRecharge PayRecharge = payRechargeMapper.getPayRechargeById(id);
		return PayRecharge;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<PayRecharge> getListOfPayRechargesByUsername(PayRecharge payRecharge, Pagination<PayRecharge> pagination) {
		PageHelper.startPage(pagination);
		return payRechargeMapper.getListOfPayRechargesByUsername(payRecharge);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PayRecharge addPayRecharge(PayRecharge payRecharge) {
		Assert.notNull(payRecharge);
		payRechargeMapper.addPayRecharge(payRecharge);
		PayRecharge result = payRechargeMapper.getPayRechargeById(payRecharge.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PayRecharge updatePayRecharge(PayRecharge payRecharge) {
		Assert.notNull(payRecharge);
		payRechargeMapper.updatePayRecharge(payRecharge);
		PayRecharge result = payRechargeMapper.getPayRechargeById(payRecharge.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getId() {
		return payRechargeMapper.getId();
	}

}
