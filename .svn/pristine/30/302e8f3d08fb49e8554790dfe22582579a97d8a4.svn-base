package com.tiamaes.bike.connector.protocol.handler;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.config.ChannelRepository;
import com.tiamaes.bike.connector.ParkServiceInterface;
import com.tiamaes.bike.connector.VehicleServiceInterface;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8003;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0003;

import io.netty.channel.Channel;

/**
 * 终端注册0x0003
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0003Handler implements ProtocolHandler<Received0003> {
	private static Logger logger = LogManager.getLogger(Protocol0003Handler.class);
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private VehicleServiceInterface vehicleService;
	@Autowired
	private ParkServiceInterface parkService;
	@Override
	public Message execute(Channel channel,Received received) throws Exception {
		Header header = received.getHeader();
		Received0003 received0003 = new Received0003(received.getHeader(), received.getBytes());
		Received0003.Body receive0003Body=received0003.getBody();
		int terminalId = header.getTerminalId();
		com.tiamaes.bike.connector.protocol.message.Header.Type termimalType=header.getTerminalType();
		/**
		 * 鉴权码，【设备】注册后才会获得
		 */
		String authentication="";
		Message8003 response = new Message8003(header,Message8003.Result.NOTEXISTED,authentication);
		/**
		 * 注册结果：0成功，1【设备】已被注册，2数据库中无该【设备】
		 * 查询数据库判断车辆和终端是否已经注册，如果都有并且没有注册，则更新【设备】(锁或者锁桩)的注册状态
		 */
		if(com.tiamaes.bike.connector.protocol.message.Header.Type.LOCK.equals(termimalType))
		{//如果是【锁】,根据terminalId获取信息
			Vehicle vehicle=vehicleService.getVehicleById(String.valueOf(terminalId));
			if(vehicle!=null)
			{
				boolean registered=vehicle.isRegistered();
				if(registered)
				{
					response.getBody().setAcode(authentication);
					response.getBody().setResult(Message8003.Result.REGISTERED);
				}else
				{
					//TODO 其他字段，现在来源与用途尚不明确，先不处理
					String simNo=receive0003Body.getSimNo();
					String producerId=receive0003Body.getProducerId();
					String device_terminalId=receive0003Body.getTerminalId();
					String device_terminalType=receive0003Body.getTerminalType();
					if(simNo!=null)
					{
						vehicle.setSimNo(simNo);
					}
					vehicle.setRegistered(true);
					vehicle.setCreateDate(new Date());
					vehicleService.updateVehicle(vehicle, vehicle.getId());
					response.getBody().setAcode(authentication);
					response.getBody().setResult(Message8003.Result.SUCCESS);
				}
			}
		}
		if(com.tiamaes.bike.connector.protocol.message.Header.Type.STATION.equals(termimalType))
		{//如果是【锁桩】
			Park park=parkService.getParkById(String.valueOf(terminalId));
			if(park!=null)
			{
				boolean registered=park.isRegistered();
				if(registered)
				{
					response.getBody().setAcode(authentication);
					response.getBody().setResult(Message8003.Result.REGISTERED);
				}else
				{
					park.setRegistered(true);
					park.setCreateTime(new Date());
					parkService.updatePark(park, park.getId());
					response.getBody().setAcode(authentication);
					response.getBody().setResult(Message8003.Result.SUCCESS);
				}
			}
		}
		String address = channel.remoteAddress().toString();
		if(logger.isDebugEnabled()){
			logger.debug("Client [{}] has bean authorized。address [{}]", terminalId,address);
		}
		return response;
	}

	
}
