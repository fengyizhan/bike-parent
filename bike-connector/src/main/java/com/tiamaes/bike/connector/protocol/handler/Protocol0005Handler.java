package com.tiamaes.bike.connector.protocol.handler;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.bean.connector.Device;
import com.tiamaes.bike.common.bean.connector.VehicleOnOffLineInfo;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.config.ChannelRepository;
import com.tiamaes.bike.connector.ParkServiceInterface;
import com.tiamaes.bike.connector.VehicleServiceInterface;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0005;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * 终端鉴权
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0005Handler implements ProtocolHandler<Received0005> {
	private static Logger logger = LogManager.getLogger(Protocol0005Handler.class);
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private VehicleServiceInterface vehicleService;
	@Autowired
	private ParkServiceInterface parkService;
	@Autowired
	@Qualifier("kafkaStringTemplate")
	private KafkaTemplate<String, String> kafkaStringTemplate;
	
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		int terminalId = header.getTerminalId();
		com.tiamaes.bike.connector.protocol.message.Header.Type termimalType=header.getTerminalType();
		Received0005 received0005 = new Received0005(header, received.getBytes());
		String aCode=received0005.getBody().getAcode();
		Device target = null;
		Attribute<Device> attribute = channel.attr(AttributeKey.valueOf("target"));
		boolean authenticationRight=false;
		Message8001 response = new Message8001(header, Result.SUCCESS);
		String authentication="";
		/**
		 * 从库中查出设备(锁或者锁桩)
		 * 通过terminalId查询数据库返回对应的鉴权码
		 */
		if(com.tiamaes.bike.connector.protocol.message.Header.Type.LOCK.equals(termimalType))
		{//如果是【锁】,根据terminalId获取信息
			Vehicle vehicle=vehicleService.getVehicleById(String.valueOf(terminalId));
			if(vehicle!=null)
			{
				authentication=vehicle.getAuthentication();
				target=vehicle;
			}
		}
		if(com.tiamaes.bike.connector.protocol.message.Header.Type.STATION.equals(termimalType))
		{//如果是【锁桩】
			Park park=parkService.getParkById(String.valueOf(terminalId));
			if(park!=null)
			{
				authentication=park.getAuthentication();
				target=park;
			}
		}
		if(authentication!=null&&StringUtils.isNotEmpty(authentication)&&aCode.equals(authentication))
		{
			authenticationRight=true;
		}
		// 获取传入的鉴权码与查询的鉴权码匹配
		if(authenticationRight)
		{
			/**
			 * 	更新数据库中的该设备的鉴权码
			 *	如果是鉴权包，并且鉴权通过，则将对应【设备】信息放入连接信息中
			 */
			attribute.set(target);
			channelRepository.put(Integer.toHexString(channel.hashCode()), channel);
			if(com.tiamaes.bike.connector.protocol.message.Header.Type.LOCK.equals(termimalType))
			{//如果是【锁】
				/**
				 * 车辆上线
				 */
				VehicleOnOffLineInfo vehicleOnOffLineInfo=new VehicleOnOffLineInfo();
				vehicleOnOffLineInfo.setCreateDate(new Date());
				vehicleOnOffLineInfo.setState(VehicleOnOffLineInfo.State.ONLINE);
				kafkaStringTemplate.send(MessageBuilder.withPayload(vehicleOnOffLineInfo)
				.setHeader(KafkaHeaders.TOPIC, vehicleOnOffLineInfo.getClass().getName()).build());
			}
		}else
		{
			channelRepository.remove(Integer.toHexString(channel.hashCode()));
			channel.close();
			response.getBody().setResult(Result.ERROR);
			return response;
		}
		
		String address = channel.remoteAddress().toString();
		
		if(logger.isDebugEnabled()){
			logger.debug("Client [{}] has bean authorized。address [{}]", terminalId,address);
		}
		return response;
	}

}
