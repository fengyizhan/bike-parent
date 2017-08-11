package com.tiamaes.bike.connector.protocol.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.bean.connector.VehicleWarningInfo;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.DirectionType;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.system.PointVector.Info.State;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received0100;

import io.netty.channel.Channel;

/**
 * 位置及车辆状态信息汇报包0x0100
 * 
 * @author lsl
 *
 */
@Component
public class Protocol0100Handler implements ProtocolHandler<Received0100> {
	private static Logger logger = LogManager.getLogger(Protocol0100Handler.class);
	@Autowired
	@Qualifier("kafkaStringTemplate")
	private KafkaTemplate<String, String> kafkaStringTemplate;
	@Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;
	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		int terminalId = header.getTerminalId();
		Received0100 received0100 = new Received0100(header, received.getBytes());
		/*
		 * 将得到的位置信息封装到对应的消费程序类中
		 * 返回通用应答
		 */
		PointVector pointvector=new PointVector();
		PointVector.Center center =new PointVector.Center();
		center.setLng(received0100.getBody().getLongitude());
		center.setLat(received0100.getBody().getAtitude());
		PointVector.Info info=new PointVector.Info();
		info.setDate(received0100.getBody().getTime());
		info.setDirection(received0100.getBody().getDirection());
		info.setElectricity(received0100.getBody().getElectricity());
		if(received0100.getBody().getElectricity()<= 20){
            info.setState(State.LOWPOWER);
		} else {
            info.setState(State.ONLINE);
		}
		info.setLatState(received0100.getBody().isLatState());
		info.setLngState(received0100.getBody().isLngState());
		info.setElevation(received0100.getBody().getHeight());
		info.setLocationState(received0100.getBody().isLocationstate());
		info.setLock(received0100.getBody().isLock());
		info.setRunState(received0100.getBody().getRunState());
		info.setSpeed(received0100.getBody().getVelocity());
		pointvector.setCenter(center);
		pointvector.setInfo(info);
		Vehicle vehicle=new Vehicle();
		vehicle.setId(terminalId+"");
		vehicle.setInfo(info);
		vehicle.setCenter(center);
		kafkaTemplate.send(MessageBuilder.withPayload(vehicle).setHeader(KafkaHeaders.TOPIC, vehicle.getClass().getName()).build());

		VehicleWarningInfo vehiclewarn=new VehicleWarningInfo();
		vehiclewarn.setVehicle(vehicle);
		vehiclewarn.setLat(received0100.getBody().getAtitude());
		vehiclewarn.setLng(received0100.getBody().getLongitude());
		vehiclewarn.setDirectionType(DirectionType.values()[2]);
		for(int i=0;i<received0100.getBody().getWarnFlag().size();i++){
			vehiclewarn.setWarnCode(received0100.getBody().getWarnFlag().get(i));
			vehiclewarn.setWarnContent(received0100.getBody().getWarnFlag().get(i).getName());
				//推送到kafka
			kafkaTemplate.send(MessageBuilder.withPayload(vehiclewarn).setHeader(KafkaHeaders.TOPIC, vehiclewarn.getClass().getName()).build());

		}
		Message8001 response = new Message8001(header, Result.SUCCESS);
		if(logger.isDebugEnabled()){
			logger.debug("[8001][{}][{}] Location information has been processed.", terminalId);
		}
		return response;
	}

	
}
