package com.tiamaes.bike.api.information.rate.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.api.information.rate.persistence.RateMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.information.Rate;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

/**
 * @author kangty
 * 费率管理接口实现类
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class RateService implements RateServiceInterface {
	
	@Resource
	private RateMapper rateMapper;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Rate> operator;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Rate getRateById(String id) {
		Assert.notNull(id, "费率编号不能为空!");
		Rate rate = operator.get(RedisKey.RATES, id); 
		if (rate == null) {
			rate = rateMapper.getRateById(id);
			if (rate != null) {
				operator.putIfAbsent(RedisKey.RATES, String.valueOf(rate.getId()), rate);
			}
		}
		return rate;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Rate> getListOfRates(Rate rate, Pagination<Rate> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		return rateMapper.getListOfRates(rate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Rate addRate(Rate rate) {
		Assert.notNull(rate);
		Assert.notNull(rate.getStageLevel(), "费率级别不能为空");
		Assert.isTrue(!rateMapper.hasExists(null, rate.getStageLevel()), "费率级别已经存在");
		rateMapper.addRate(rate);
		Rate result = rateMapper.getRateById(rate.getId());
		if (result != null) {
			operator.putIfAbsent(RedisKey.RATES, rate.getId(), result);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Rate updateRate(Rate rate) {
		Assert.notNull(rate);
		Assert.notNull(rate.getStageLevel(), "费率级别不能为空");
		Assert.isTrue(!rateMapper.hasExists(rate.getId(), rate.getStageLevel()), "费率级别已经存在");
		rateMapper.updateRate(rate);
		Rate result = rateMapper.getRateById(rate.getId());
		if (result != null) {
			operator.put(RedisKey.STATIONS, rate.getId(), rate);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteRate(Rate rate) {
		Assert.notNull(rate, "费率信息不能为空!");
		rateMapper.deleteRate(rate);
		Rate result = rateMapper.getRateById(rate.getId());
		if (result == null) {
			operator.delete(RedisKey.RATES, rate.getId());
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean checkRateLevel(Optional<String> id, Integer stageLevel) {
		return rateMapper.hasExists(id.isPresent() ? id.get() : null, stageLevel);
	}

	@Override
	public List<Integer> getListOfRateLevels() {
		return rateMapper.getListOfRateLevels();
	}

}
