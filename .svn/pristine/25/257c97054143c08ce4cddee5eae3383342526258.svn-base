package com.tiamaes.bike.api.information.command.service;

import java.util.List;

import com.tiamaes.bike.common.bean.command.Command;

public interface CommandServiceInterface {

	/**
	 * 发送单个指令
	 * <p>
	 * 1.首先将每条下发的指令持久化<br>
	 * 2.判断当前终端是否在线<br>
	 * 3.将在线终端的命令指令推送到消息队列中，等待通讯程序消费
	 * </p>
	 * 
	 * @param command
	 * @return Command
	 */
	public Command sendCommand(Command command);

	/**
	 * 发送多个指令
	 * 
	 * @param commands
	 * @return List<Command>
	 */
	public void sendCommands(List<? extends Command> commands);

	/**
	 * 获取车载终端的离线指令列表
	 * @param id
	 * @return
	 */
	public List<Command> getListOfCommandById(String id);

	/**
	 * 获取指定命令信息
	 * 
	 * @param id
	 * @param sid
	 * @return
	 */
	public Command getCommandBy(int id, long sid);

	/**
	 * 操作指令完成
	 * 
	 * @param command
	 */
	public void completed(Command command);
}
