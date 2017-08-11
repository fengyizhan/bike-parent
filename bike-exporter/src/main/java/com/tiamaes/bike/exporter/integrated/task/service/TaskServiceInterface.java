package com.tiamaes.bike.exporter.integrated.task.service;

import java.util.List;

import com.tiamaes.bike.common.bean.integrated.Export;
import com.tiamaes.bike.common.bean.integrated.Task;

public interface TaskServiceInterface {
	
	/**
	 * 获取用户任务列表数据
	 * @return
	 */
	List<Task> getListOfTasks(Task task);
	
	/**
	 * 增加用户任务信息
	 * @param task
	 * @return
	 */
	Task addTask(Task task, Export export);
	
	/**
	 * 更新用户任务信息
	 * @param task
	 * @return
	 */
	Task updateTask(Task task);
	
	/**
	 * 根据id获取任务信息
	 * @param id
	 * @return
	 */
	Task getTaskById(String id);
	
	/**
	 * 删除任务信息
	 * @param task
	 * @throws Exception 
	 */
	void deleteTask(Task task) throws Exception;

}
