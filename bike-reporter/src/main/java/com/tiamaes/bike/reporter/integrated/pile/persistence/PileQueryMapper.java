package com.tiamaes.bike.reporter.integrated.pile.persistence;

import java.util.List;

import com.tiamaes.bike.common.bean.Parameters;
import com.tiamaes.bike.common.bean.integrated.PileRecord;

public interface PileQueryMapper {
	
	/**
	 * 获取锁桩报警记录分页数据
	 * @param parameters
	 * @return
	 */
	List<PileRecord> getListOfPileRecords(Parameters<PileRecord> parameters);

}
