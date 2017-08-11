package com.tiamaes.bike.connector.service;

import com.tiamaes.bike.common.bean.command.Command;
import com.tiamaes.bike.common.bean.command.Completed;

public interface CommandServiceInterface {
	
	/**
	 * 下发指令
	 * @param command
	 * @throws Exception
	 */
	void sendCommand(Command command) throws Exception;
	
	/**
	 * 删除下发指令
	 * @param command
	 */
	void deleteCommand(Command command);
	/**
	 * 指令完成
	 * @param completed
	 */
	void completed(Completed completed);
	/**
	 * 根据终端ID生成消息流水号
	 * @param terminalId 终端ID
	 * @return 消息流水号
	 */
	public int generateSerialNo(String terminalId);
}
