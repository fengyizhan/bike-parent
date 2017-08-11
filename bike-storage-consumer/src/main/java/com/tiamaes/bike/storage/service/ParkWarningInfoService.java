package com.tiamaes.bike.storage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.storage.persistence.ParkWarningInfoMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ParkWarningInfoService implements ParkWarningInfoServiceInterface {
	
	@Autowired
	private ParkWarningInfoMapper parkWarningInfoMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addParkWarningInfo(PileRecord pileRecord) {
		Assert.notNull(pileRecord, "停放区报警信息不能为空");
		pileRecord.setId(UUIDGenerator.getUUID());
		pileRecord.setCreateDate(new Date());
		parkWarningInfoMapper.addParkWarningInfo(pileRecord);
	}

}
