package com.tiamaes.bike.reporter.integrated.pile.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.reporter.integrated.pile.persistence.PileQueryMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PileQueryService implements PileQueryServiceInterface {
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(PileQueryService.class);
	
	@Resource
	private PileQueryMapper pileQueryMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<PileRecord> getListOfPileRecords(Parameters<PileRecord> parameters, Pagination<PileRecord> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		List<PileRecord> pileRecords = pileQueryMapper.getListOfPileRecords(parameters);
		return pileRecords;
	}

}
