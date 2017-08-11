package com.tiamaes.bike.wallet.information.recharge.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.wallet.Recharge;
import com.tiamaes.bike.wallet.information.recharge.persistence.RechargeMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class RechargeService implements RechargeServiceInterface {
	
	@Resource
	private RechargeMapper rechargeMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Recharge getRechargeById(int id) {
		Assert.notNull(id, "充值记录编号不能为空!");
		Recharge recharge = rechargeMapper.getRechargeById(id);
		return recharge;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Recharge> getListOfRechargesByUsername(Recharge recharge, Pagination<Recharge> pagination) {
		Assert.notNull(recharge.getUsername(), "用户名不能为空!");
		PageHelper.startPage(pagination);
		return rechargeMapper.getListOfRechargesByUsername(recharge);
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Recharge> getListOfRecharges(Recharge recharge, Pagination<Recharge> pagination) {
		PageHelper.startPage(pagination);
		return rechargeMapper.getListOfRecharges(recharge);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Recharge addRecharge(Recharge recharge) {
		Assert.notNull(recharge);
		rechargeMapper.addRecharge(recharge);
		Recharge result = rechargeMapper.getRechargeById(recharge.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Recharge updateRecharge(Recharge recharge) {
		Assert.notNull(recharge);
		rechargeMapper.updateRecharge(recharge);
		Recharge result = rechargeMapper.getRechargeById(recharge.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getId() {
		return rechargeMapper.getId();
	}

}
