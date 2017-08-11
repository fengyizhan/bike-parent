package com.tiamaes.bike.wallet.information.consume.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.wallet.information.consume.persistence.ConsumeMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ConsumeService implements ConsumeServiceInterface {
	
	@Resource
	private ConsumeMapper consumeMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Consume getConsumeById(int id) {
		Assert.notNull(id, "消费记录编号不能为空!");
		Consume consume = consumeMapper.getConsumeById(id);
		return consume;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Consume> getListOfConsumesByUsername(Consume consume, Pagination<Consume> pagination) {
		Assert.notNull(consume.getUsername(), "用户名不能为空!");
		PageHelper.startPage(pagination);
		return consumeMapper.getListOfConsumesByUsername(consume);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Consume addConsume(Consume consume) {
		Assert.notNull(consume);
		consumeMapper.addConsume(consume);
		Consume result = consumeMapper.getConsumeById(consume.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Consume updateConsume(Consume consume) {
		Assert.notNull(consume);
		consumeMapper.updateConsume(consume);
		Consume result = consumeMapper.getConsumeById(consume.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getId() {
		return consumeMapper.getId();
	}

}
