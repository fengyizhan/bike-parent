package com.tiamaes.bike.connector.protocol.handler;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0180;

import io.netty.channel.Channel;
/**
 * 开锁包0x0180
 * @author fei
 *
 */
public class Protocol0180Handler implements ProtocolHandler<Received0180> {
	private static Logger logger = LogManager.getLogger(Protocol0180Handler.class);

	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		Received0180 received0180=new Received0180(header,received.getBytes());
		/*
		 * 1.把车辆状态更新为已借出
		 */
		Vehicle vehicle=null;
		
		/*
		 * 2.生成借出记录
		 */
		BorrowRecord borrowRecord=new BorrowRecord();
		Park startPark=vehicle.getPark();
		borrowRecord.setStartPark(startPark);
		borrowRecord.setCreateDate(new Date());
		borrowRecord.setStartTime(received0180.getBody().getUnlockTime());
		/*
		 * 3.命令completed
		 */
		return new Message8001(header, Result.SUCCESS);
	}
	
}
