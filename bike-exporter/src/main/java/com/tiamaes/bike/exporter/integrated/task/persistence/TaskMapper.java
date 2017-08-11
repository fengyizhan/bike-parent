package com.tiamaes.bike.exporter.integrated.task.persistence;

import java.util.List;

import com.tiamaes.bike.common.bean.integrated.Task;

public interface TaskMapper {
	
	/**
	 * 获取用户任务列表数据
	 * @return
	 */
	List<Task> getListOfTasks(Task task);
	
	/**
	 * 根据id获取用户任务信息
	 * @param id
	 * @return
	 */
	Task getTaskById(String id);
	
	/**
	 * 增加用户任务信息
	 * @param task
	 */
	void addTask(Task task);
	
	/**
	 * 更新用户任务信息
	 * @param task
	 */
	void updateTask(Task task);

	/**
	 * 删除用户任务信息
	 * @param task
	 */
	void deleteTask(Task task);

}
