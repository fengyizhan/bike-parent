package com.tiamaes.bike.exporter.integrated.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.bean.integrated.Export;
import com.tiamaes.bike.common.bean.integrated.Task;
import com.tiamaes.bike.common.utils.FTPClientUtils;
import com.tiamaes.bike.exporter.integrated.task.persistence.TaskMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TaskService implements TaskServiceInterface {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(TaskService.class);
	@Resource
	private TaskMapper taskMapper;
	@Value("${ftp.host}")
	private String host;	// 服务器路径
	@Value("${ftp.username}")
	private String username;// 服务器用户名
	@Value("${ftp.password}")
	private String password;// 服务器密码
	@Value("${ftp.filepath}")
	private String filepath;// 服务器excel文件夹路径
	
	@Autowired
	@Qualifier("kafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Task> getListOfTasks(Task task) {
		return taskMapper.getListOfTasks(task);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Task addTask(Task task, Export export) {
		Assert.notNull(task);
		Assert.notNull(task.getUserId(), "用户id不能为空");
		taskMapper.addTask(task);
		task = taskMapper.getTaskById(task.getId());
		export.setZipFileName(task.getFileName());
		task.setExport(export);
		kafkaTemplate.send(MessageBuilder.withPayload(task).setHeader(KafkaHeaders.TOPIC, task.getClass().getName()).build());
		return task;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Task updateTask(Task task) {
		Assert.notNull(task);
		Assert.notNull(task.getUserId(), "用户id不能为空");
		taskMapper.updateTask(task);
		Task result = taskMapper.getTaskById(task.getId());
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Task getTaskById(String id) {
		return taskMapper.getTaskById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteTask(Task task) throws Exception {
		Assert.notNull(task, "任务不能为空!");
		taskMapper.deleteTask(task);
		FTPClientUtils.deleteFile(host, username, password, filepath + task.getFileName());
	}

}
