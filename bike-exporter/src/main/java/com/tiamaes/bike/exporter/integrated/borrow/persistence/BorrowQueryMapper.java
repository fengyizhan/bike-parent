package com.tiamaes.bike.exporter.integrated.borrow.persistence;

import java.util.List;

import com.tiamaes.bike.common.bean.integrated.BorrowRecord;

public interface BorrowQueryMapper {
	
	/**
	 * 获取车辆租借记录数据
	 * @param borrowRecord
	 * @return
	 */
	List<BorrowRecord> getListOfBorrowRecords(BorrowRecord borrowRecord);
	
}
