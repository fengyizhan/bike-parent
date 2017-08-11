package com.tiamaes.bike.wallet.information.deposit.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.wallet.Deposit;
import com.tiamaes.bike.wallet.information.deposit.persistence.DepositMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class DepositService implements DepositServiceInterface {
	
	@Resource
	private DepositMapper depositMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Deposit getDepositById(int id) {
		Assert.notNull(id, "充值记录编号不能为空!");
		Deposit deposit = depositMapper.getDepositById(id);
		return deposit;
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Deposit> getListOfDeposits(Deposit deposit, Pagination<Deposit> pagination) {
		PageHelper.startPage(pagination);
		return depositMapper.getListOfDeposits(deposit);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Deposit addDeposit(Deposit deposit) {
		Assert.notNull(deposit);
		depositMapper.addDeposit(deposit);
		Deposit result = depositMapper.getDepositById(deposit.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Deposit updateDeposit(Deposit deposit) {
		Assert.notNull(deposit);
		depositMapper.updateDeposit(deposit);
		Deposit result = depositMapper.getDepositById(deposit.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getId() {
		return depositMapper.getId();
	}

}
