package com.tiamaes.bike.reporter.integrated.borrow.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;

public interface BorrowQueryMapper {
	
	/**
	 * 获取借还记录分页数据
	 * @param parameters
	 * @return
	 */
	List<BorrowRecord> getListOfBorrowRecords(Parameters<BorrowRecord> parameters);

	/**
	 * 获取指定车辆的借车记录
	 * @param bikeId
	 * @return
	 */
	List<BorrowRecord> getListOfBikeBorrowRecords(@Param("bikeId")int bikeId);
	
	/**
	 * 获取指定车辆已使用时间
	 * @param bikeId
	 * @return
	 */
	String getBikeUserTimeInfo(@Param("bikeId")int bikeId);
	
	/**
	 * 获取指定借车记录
	 * @param borrowId
	 * @return
	 */
	BorrowRecord getBorrowRecord(@Param("borrowId")String borrowId);
	
}
