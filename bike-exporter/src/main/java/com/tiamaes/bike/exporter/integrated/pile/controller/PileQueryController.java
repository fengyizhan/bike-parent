package com.tiamaes.bike.exporter.integrated.pile.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.common.bean.integrated.Task;
import com.tiamaes.bike.common.bean.integrated.Schedule.Progress;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.exporter.integrated.task.service.TaskServiceInterface;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

@RestController
@RequestMapping("/integrated/pile")
public class PileQueryController {
	private static Logger logger = LogManager.getLogger(PileQueryController.class);
	@Resource
	private TaskServiceInterface taskService;
	
	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST, produces = {"application/json" })
	public @ResponseBody Object exportExcel(@RequestBody PileRecord pileRecord, 
			@CurrentUser User operator) {
		Task task = null;
		try {
			task = taskService.addTask(new Task(UUIDGenerator.getUUID(), operator.getUsername(), Progress.WILL), pileRecord);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return task;
	}
	
}
