package com.tiamaes.bike.monitor.listener;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.system.PointVector.Info;
import com.tiamaes.bike.common.bean.system.PointVector.Info.RunState;
import com.tiamaes.bike.common.bean.system.PointVector.Info.State;
import com.tiamaes.bike.common.bean.connector.ParkStatusInfo;
import com.tiamaes.bike.common.bean.connector.ResponseInfo;
import com.tiamaes.bike.common.bean.connector.VehicleOnOffLineInfo;
import com.tiamaes.bike.common.bean.connector.VehicleWarningInfo;
import com.tiamaes.bike.common.bean.connector.ResponseInfo.ResponseState;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.common.bean.integrated.Schedule;

@Component
public class Listener{
	private static Logger logger = LogManager.getLogger(Listener.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, User> userOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, Park> parkOperator;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, PointVector> pointVectorOperator;
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 消费车辆实时状态信息
	 * @param status
	 * @throws Exception
	 */
	@KafkaListener(id = "listen1", topicPattern = "com.tiamaes.bike.common.bean.information.Vehicle")
	public void listen1(@Payload Vehicle vehicle) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received vehicle : {}-------------------", vehicle);
		}
		if(vehicle != null && vehicle.getId() != null){
			//修改低电量车辆数量
			if(State.LOWPOWER == vehicle.getInfo().getState()){
				setOperator.add(RedisKey.VEHICLES_LOWPOWER, vehicle.getId());
			}else{
				setOperator.remove(RedisKey.VEHICLES_LOWPOWER, vehicle.getId());
			}
			if(RunState.BORROWED == vehicle.getInfo().getRunState()){
				setOperator.add(RedisKey.VEHICLES_USING, vehicle.getId());
			}else{
				setOperator.remove(RedisKey.VEHICLES_USING, vehicle.getId());
			}
			//获取实时用户信息
			User user = userOperator.get(RedisKey.VEHICLES_DRIVERS_VEHICLEID, vehicle.getId());
			//当天趟次
			String dayTripCount = (String) stringRedisTemplate.opsForHash().get(RedisKey.VEHICLES_TRIPS, vehicle.getId());
			
			if(user!=null){
				Info info = vehicle.getInfo();
				info.setUser(user);
				if(StringUtils.isNotBlank(dayTripCount)){
					//当天趟次
					info.setDayTrip(Integer.valueOf(dayTripCount));
				}
				vehicle.setInfo(info);
			}
			
			template.convertAndSend("/topic/status", vehicle);
		}
	}
	
	/**
	 * 消费车辆报警信息
	 * @param warning
	 * @throws Exception
	 */
	@KafkaListener(id = "listen2", topicPattern = "com.tiamaes.bike.common.bean.connector.VehicleWarningInfo")
	public void listen2(@Payload VehicleWarningInfo warning) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received warning : {}-------------------", warning);
		}
		if(warning != null && warning.getVehicle() != null && warning.getVehicle().getId() != null){
			template.convertAndSend("/topic/warning", warning);
		}
	}
	
	/**
	 * 车辆指令下发响应消息
	 * @param response
	 * @throws Exception
	 */
	@KafkaListener(id = "listen3", topics = "com.tiamaes.bike.common.bean.connector.ResponseInfo")
	public void listen3(@Payload ResponseInfo response) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received response : {}-------------------", response);
		}
		if(response != null && response.getId() != null){
			template.convertAndSend(String.format("/topic/response/%s", response.getId()), ResponseState.SUCCESS.equals(response.getResponseState()));
		}
	}
	
	/**
	 * 车辆上下线消息
	 * @param onoff
	 * @throws Exception
	 */
	@KafkaListener(id = "listen4", topics = "com.tiamaes.bike.common.bean.connector.VehicleOnOffLineInfo")
	public void listen4(@Payload VehicleOnOffLineInfo onoff) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received onoff : {}-------------------", onoff);
		}
		Vehicle vehicle = onoff.getVehicle();
		
		if(onoff != null && onoff.getVehicle() != null && onoff.getVehicle().getId() != null){
			//获取车辆详情信息
			PointVector pointVector = pointVectorOperator.get(RedisKey.VEHICLES_POINTVECTORS, vehicle.getId());
			
			if(onoff.getState().equals(VehicleOnOffLineInfo.State.ONLINE)){
				//增加统计上下线的操作，更新缓存上下线数量
				setOperator.add(RedisKey.VEHICLES_ONLINE, onoff.getVehicle().getId());
				setOperator.remove(RedisKey.VEHICLES_OFFLINE, onoff.getVehicle().getId());
				Info info = pointVector.getInfo();
				if(info==null){
					info = new Info();
				} 
				info.setState(State.ONLINE);
				vehicle.setInfo(info);
			}
			if(onoff.getState().equals(VehicleOnOffLineInfo.State.OFFLINE)){
				//下线的时候，更新缓存上下线数量。
				setOperator.add(RedisKey.VEHICLES_OFFLINE, onoff.getVehicle().getId());
				setOperator.remove(RedisKey.VEHICLES_ONLINE, onoff.getVehicle().getId());
				Info info = pointVector.getInfo();
				if(info==null){
					info = new Info();
				}
				info.setState(State.OFFLINE);
				vehicle.setInfo(info);
			}
			onoff.setVehicle(vehicle);
			
			template.convertAndSend("/topic/onoff", onoff);
		}
	}
	
	/**
	 * 消费用户任务信息
	 * @param status
	 * @throws Exception
	 */
	@KafkaListener(id = "listen5", topicPattern = "com.tiamaes.bike.common.bean.integrated.Schedule")
	public void listen5(@Payload Schedule schedule) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received schedule : {}-------------------", schedule.toString());
		}
		template.convertAndSend("/topic/schedule/" + schedule.getId(), schedule);
	}
	
	/**
	 * 消费停放区报警信息
	 * @param parkVehicleInfo
	 * @throws Exception
	 */
	@KafkaListener(id = "listen6", topics = "com.tiamaes.bike.common.bean.integrated.PileRecord")
	public void listen6(@Payload PileRecord pileRecord) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received pileRecord : {}-------------------", pileRecord);
		}
		if(pileRecord != null && pileRecord.getPark() != null && pileRecord.getPark().getId() != null){
			template.convertAndSend("/topic/warning", pileRecord);
		}
	}
	
	/**
	 * 消费停放区状态信息
	 * @param parkStatusInfo
	 * @throws Exception
	 */
	@KafkaListener(id = "listen7", topics = "com.tiamaes.bike.common.bean.connector.ParkStatusInfo")
	public void listen7(@Payload ParkStatusInfo parkStatusInfo) throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("------------------Received parkStatusInfo : {}-------------------", parkStatusInfo);
		}
		if(parkStatusInfo != null && parkStatusInfo.getPark() != null){
			Park park = parkOperator.get(RedisKey.PARKS, parkStatusInfo.getPark().getId());
			park.setVehicles(parkStatusInfo.getVehicles());
			template.convertAndSend("/topic/park", park);
		}
	}

}
