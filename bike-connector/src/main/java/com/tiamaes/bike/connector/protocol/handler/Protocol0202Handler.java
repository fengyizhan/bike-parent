package com.tiamaes.bike.connector.protocol.handler;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.command.UnlockCommand;
import com.tiamaes.bike.common.bean.command.UnlockCommand.Control;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.config.ChannelRepository;
import com.tiamaes.bike.connector.DriverServiceInterface;
import com.tiamaes.bike.connector.MessageServiceInterface;
import com.tiamaes.bike.connector.ParkServiceInterface;
import com.tiamaes.bike.connector.VehicleServiceInterface;
import com.tiamaes.bike.connector.WalletUserServiceInterface;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0202;
import com.tiamaes.bike.connector.service.CommandServiceInterface;
import com.tiamaes.security.core.userdetails.User;

import io.netty.channel.Channel;

/**
 * 刷卡借还车消息包0x0202
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0202Handler implements ProtocolHandler<Received0202> {
	private static Logger logger = LogManager.getLogger(Protocol0202Handler.class);
	@Autowired
	private ChannelRepository channelRepository;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String,User> vehicleUserRedisTemplate;
	@Autowired
	private VehicleServiceInterface vehicleService;
	@Autowired
	private ParkServiceInterface parkService;
	@Autowired
	private DriverServiceInterface driverService;
	@Autowired
	private WalletUserServiceInterface walletUserService;
	@Autowired
	@Qualifier("kafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String, BorrowRecord> borrowRecordRedisTemplate;
	@Autowired
	private MessageServiceInterface messageService;
	@Autowired
	private CommandServiceInterface commandService;
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		int terminalId = header.getTerminalId();
		Received0202 received0202 = new Received0202(header, received.getBytes());
		Received0202.Body body=received0202.getBody();
		//1：借车/0：还车
		int bikeState=body.getBikestateflag();
		//车辆编号
		String bikeNum=body.getBikenum();
		//卡号
		String cardNum=body.getCardnum();
		//刷卡借还车业务处理
		Vehicle vehicle=null;
		User driver=null;
		Message8001 response = new Message8001(header, Result.SUCCESS);
		try
		{
			/**
			 * 1:借车；解锁业务
			 */
			if(bikeState==1)
			{
				String vehicleId=bikeNum;
				vehicle=vehicleService.getVehicleById(vehicleId);
				driver=driverService.getDriverByIcCard(cardNum);
				/**
				 * 将当前车辆与【租借人】关联，便于后续逻辑处理
				 */
				vehicleUserRedisTemplate.opsForHash().put(RedisKey.VEHICLES_DRIVERS_VEHICLEID,vehicle.getId(),driver);
			}else
			{
			/**
			 * 0:还车；关锁业务
			 */
			}
			/**
			 * 发送解锁指令，下发给通讯程序后续处理
			 */
			UnlockCommand unlockCommand=new UnlockCommand();
			unlockCommand.setId(UUIDGenerator.getUUID());
			unlockCommand.setClazz(UnlockCommand.class.getName());
			unlockCommand.setSeqNo(new Date().getTime());
			unlockCommand.setTerminalId(Integer.valueOf(vehicle.getId()));
			unlockCommand.setControl(Control.UNLOCK);
			unlockCommand.setDriverId(driver.getUsername());
			unlockCommand.setVehicleId(vehicle.getId());
			unlockCommand.setCreateTime(new Date());
			commandService.sendCommand(unlockCommand);
		}catch(Exception e)
		{
			response = new Message8001(header, Result.ERROR);
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("[8001][{}][{}] Location information has been processed.", terminalId);
		}
		return response;
	}
}
