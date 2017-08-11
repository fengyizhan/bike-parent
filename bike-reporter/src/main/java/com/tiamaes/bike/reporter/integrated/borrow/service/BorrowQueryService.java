package com.tiamaes.bike.reporter.integrated.borrow.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.reporter.integrated.borrow.persistence.BorrowQueryMapper;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BorrowQueryService implements BorrowQueryServiceInterface {
	
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(BorrowQueryService.class);
	
	@Resource
	private BorrowQueryMapper borrowQueryMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BorrowRecord> getListOfBorrowRecords(Parameters<BorrowRecord> parameters,
			Pagination<BorrowRecord> pagination) {
		Assert.notNull(pagination, "分页对象不能为空");
		PageHelper.startPage(pagination);
		return borrowQueryMapper.getListOfBorrowRecords(parameters);
	}

	@Override
	public List<BorrowRecord> getListOfBikeBorrowRecord(int bikeId) {
		return borrowQueryMapper.getListOfBikeBorrowRecords(bikeId);
	}

	@Override
	public String getBikeUserTimeInfo(int bikeId) {
		return borrowQueryMapper.getBikeUserTimeInfo(bikeId);
	}
	
	@Override
	public BorrowRecord getBorrowRecord(String borrowId) {
		return borrowQueryMapper.getBorrowRecord(borrowId);
	}

}
