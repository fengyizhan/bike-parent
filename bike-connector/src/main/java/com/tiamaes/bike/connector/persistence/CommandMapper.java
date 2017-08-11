package com.tiamaes.bike.connector.persistence;

import com.tiamaes.bike.common.bean.connector.command.Command;

public interface CommandMapper {
	
	/**
	 * 删除下发指令
	 * @param command
	 */
	void deleteCommand(Command command);

}
