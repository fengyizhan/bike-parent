package com.tiamaes.bike.connector.protocol.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.JPushContent;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.common.bean.integrated.ParkVehicleUseRecord;
import com.tiamaes.bike.common.bean.integrated.UseType;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.system.PointVector.Center;
import com.tiamaes.bike.common.bean.system.User;
import com.tiamaes.bike.common.utils.LocationUtils;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.connector.MessageServiceInterface;
import com.tiamaes.bike.connector.ParkServiceInterface;
import com.tiamaes.bike.connector.VehicleServiceInterface;
import com.tiamaes.bike.connector.protocol.message.Header;
import com.tiamaes.bike.connector.protocol.message.Message;
import com.tiamaes.bike.connector.protocol.message.Message8001;
import com.tiamaes.bike.connector.protocol.message.Message8001.Result;
import com.tiamaes.bike.connector.protocol.message.Received;
import com.tiamaes.bike.connector.protocol.message.Received0012;
import com.tiamaes.bike.connector.protocol.message.Received0012.Body;

import io.netty.channel.Channel;

/**
 * 车辆开锁、关锁执行结果0x0012
 * 
 * @author Chen
 *
 */
@Component
public class Protocol0012Handler implements ProtocolHandler<Received0012> {
	@Autowired
	@Qualifier("kafkaTemplate")
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String, BorrowRecord> borrowRecordRedisTemplate;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String, User> vehicleUserRedisTemplate;
	@Autowired
	private VehicleServiceInterface vehicleService;
	@Autowired
	private ParkServiceInterface parkService;
	@Autowired
	private MessageServiceInterface messageService;

	@Override
	public Message execute(Channel channel, Received received) throws Exception {
		Header header = received.getHeader();
		Received0012 received0012 = new Received0012(header, received.getBytes());
		Body body = received0012.getBody();
		/**
		 * 如果是在停车区停车的时候，由【锁桩】会上传上来；
		 * 如果是在非停车区，不会有parkId,只会有经纬度，那么就是临时停车，等会儿用手机APP蓝牙解锁；
		 */
		int vehicleId = body.getVehicleId();
		/**
		 * 如果在停车区里，terminalId代表【桩】；
		 * 如果不在停车区里，terminalId代表【锁】；
		 */
		int terminalId = header.getTerminalId();
		Header.Type terminalType=header.getTerminalType();
		/**
		 * 【桩】ID
		 */
		int parkId=0;
		if(Header.Type.STATION.equals(terminalType))
		{
			parkId=terminalId;
		}
		double lat = body.getLat();// 纬度
		double lng = body.getLng();// 经度
		Vehicle vehicle = vehicleService.getVehicleById(String.valueOf(vehicleId));
		User driver = (User) vehicleUserRedisTemplate.opsForHash().get(RedisKey.VEHICLES_DRIVERS_VEHICLEID,
				vehicle.getId());
		if (Received0012.Result.SUCCESS.equals(body.getResult())) {
			switch (body.getLockstate()) {
			case OPEN:
				switch (body.getResult()) {// 开锁成功
				case SUCCESS:
					/**
					 * 更新车辆状态为【已借出】
					 */
					PointVector.Info vehicleInfo = vehicle.getInfo();
					vehicleInfo.setRunState(PointVector.Info.RunState.BORROWED);
					/**
					 * 生成借车记录 当解锁开车时，车辆所在位置为【起始场区】； 当还车关闭时，车辆所在位置为【结束场区】；
					 * 车辆当前所在的停车区是由【通讯程序】动态更新的，后台只管取用即可；
					 */
					Park startPark = vehicle.getPark();
					if (startPark==null) {
						/**
						 * 如果在【锁桩】范围内停车，锁桩会上传【parkId】
						 */
						startPark = parkService.getParkById(String.valueOf(parkId));
					}
					
					/**
					 * 生成租借记录缓存，其余记录的字段，当还车流程结束后，会由【通讯程序】补充完整
					 */
					String borrowId = UUIDGenerator.getUUID();

					BorrowRecord borrowRecord = new BorrowRecord();
					borrowRecord.setId(borrowId);
					borrowRecord.setUser(driver);
					Date startTime = new Date();
					borrowRecord.setCreateDate(startTime);
					borrowRecord.setStartTime(startTime);
					borrowRecord.setVehicle(vehicle);
					borrowRecord.setStartPark(startPark);
					borrowRecordRedisTemplate.opsForHash().put(RedisKey.DRIVERS_BORROW_RECORDS, driver.getUsername(),
							borrowRecord);
					// 修改车辆租借关联
					vehicle.setBorrowId(borrowId);
					vehicleService.updateVehicle(vehicle, vehicle.getId());
					/**
					 * 发送用户用车日志（借车）
					 */
					ParkVehicleUseRecord open_ParkVehicleUseRecord = new ParkVehicleUseRecord();
					open_ParkVehicleUseRecord.setCreateDate(new Date());
					open_ParkVehicleUseRecord.setPark(startPark);
					open_ParkVehicleUseRecord.setUser(driver);
					open_ParkVehicleUseRecord.setUseType(UseType.BORROW);
					open_ParkVehicleUseRecord.setVehicle(vehicle);
					open_ParkVehicleUseRecord.setType(com.tiamaes.bike.common.bean.integrated.Type.APP);
					kafkaTemplate.send(MessageBuilder.withPayload(open_ParkVehicleUseRecord)
							.setHeader(KafkaHeaders.TOPIC, open_ParkVehicleUseRecord.getClass().getName()).build());
					/*
					 * 给用户发送一个系统通知
					 */
					JPushContent borrowReturn = new JPushContent();
					com.tiamaes.bike.common.bean.Result unlockResult = new com.tiamaes.bike.common.bean.Result();
					unlockResult.setSuccess(true);
					unlockResult.setMessage("车辆解锁成功，开始计时");
					unlockResult.setType(com.tiamaes.bike.common.bean.Result.TEXT_TYPE);
					borrowReturn.setContent(objectMapper.writeValueAsString(unlockResult));
					borrowReturn.setMobiles(driver.getUsername());
					messageService.sendJpush(borrowReturn);
					break;
				default:
					break;
				}
				break;
			case CLOSE:
				switch (body.getResult()) {// 关锁成功
				case SUCCESS:
					Park endPark = null;
					if (parkId > 0) {
						/**
						 * 如果在【锁桩】范围内停车，锁桩会上传【parkId】
						 */
						endPark = parkService.getParkById(String.valueOf(parkId));
					} else {
						/**
						 * 如果是【锁桩】设备异常，用的【车锁】2G网络上传，那么获取不到【parkId】
						 * 如果上传了当前APP的ＧＰＳ定位数据，那么搜索最近的【锁桩】，认为是还车锁桩
						 */
						if (lat > 0 && lng > 0) {
							Center center = new Center();
							center.setLat(lat);
							center.setLng(lng);
							center.setRotation(1);
							List<Park> parks = parkService.getDataOfCountData(center);
							if (parks != null && parks.size() > 0) {
								endPark = parks.get(0);
							} else {
								/**
								 * 非【停车区】临时停靠：匹配不到一个最近【停车区】，则认为是不在【停车区】内的停车；
								 * 需要极光推送消息，手机APP界面变化至‘停车等待’，并显示‘解锁’按钮，
								 * 稍后以蓝牙方式解锁；
								 */
								JPushContent pushContent = new JPushContent();
								pushContent.setMobiles(driver.getUsername());
								com.tiamaes.bike.common.bean.Result result_consume = new com.tiamaes.bike.common.bean.Result();
								result_consume.setSuccess(true);
								result_consume.setMessage("停车等待");
								result_consume.setType(com.tiamaes.bike.common.bean.Result.JSON_TYPE);
								pushContent.setContent(objectMapper.writeValueAsString(result_consume));
								pushContent.setMobiles(driver.getUsername());
								com.tiamaes.bike.common.bean.Result pushResult = messageService.sendJpush(pushContent);
								String pushLog = pushResult.getMessage();
							}
						}
					}
					// 检查当前人员是否已经租借一辆车
					BorrowRecord borrowRecord = (BorrowRecord) borrowRecordRedisTemplate.opsForHash()
							.get(RedisKey.DRIVERS_BORROW_RECORDS, driver.getUsername());
					// 当前车辆最新位置状态是在【车辆关锁】时的【停车区】内，代表‘还车位置’
					// 车辆借出时位置状态是在【车辆开锁】时的【停车区】内，代表‘租车位置’
					Park startPark = borrowRecord.getStartPark();
					borrowRecord.setEndPark(endPark);
					Date endTime = new Date();
					Date startTime = borrowRecord.getStartTime();
					/**
					 * 自行车使用时长、距离等
					 */
					long usedTime = (endTime.getTime() - startTime.getTime()) / 1000; // 使用时长：毫秒值
					borrowRecord.setCountTime(String.valueOf(usedTime));
					// 米转化为千米后，保留小数点后面两位
					double kiloMeters = LocationUtils.getDistance(startPark.getLat(), startPark.getLng(),
							endPark.getLat(), endPark.getLng()) / 1000;// 骑行距离：米
					BigDecimal bigDecimal = new BigDecimal(kiloMeters);
					double kiloMeters_2 = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					borrowRecord.setEndTime(endTime);
					borrowRecord.setKilometers(kiloMeters_2);
					// 检查当前人员是否已经租借一辆车【骑行中】的标志位，租车流程结束后要清空
					borrowRecordRedisTemplate.opsForHash().delete(RedisKey.DRIVERS_BORROW_RECORDS,
							driver.getUsername());
					/**
					 * 极光推送【租借行程】记录，供APP使用
					 */
					JPushContent pushContent = new JPushContent();
					pushContent.setMobiles(driver.getUsername());
					com.tiamaes.bike.common.bean.Result result_consume = new com.tiamaes.bike.common.bean.Result();
					result_consume.setSuccess(true);
					result_consume.setMessage("还车消费结束");
					result_consume.setType(com.tiamaes.bike.common.bean.Result.JSON_TYPE);
					result_consume.getData().put("borrow", borrowRecord);
					pushContent.setContent(objectMapper.writeValueAsString(result_consume));
					com.tiamaes.bike.common.bean.Result pushResult = messageService.sendJpush(pushContent);
					String pushLog = pushResult.getMessage();
					/**
					 * 发送用户用车日志（还车）
					 */
					ParkVehicleUseRecord close_ParkVehicleUseRecord = new ParkVehicleUseRecord();
					close_ParkVehicleUseRecord.setCreateDate(new Date());
					close_ParkVehicleUseRecord.setPark(startPark);
					close_ParkVehicleUseRecord.setUser(driver);
					close_ParkVehicleUseRecord.setUseType(UseType.RETURN);
					close_ParkVehicleUseRecord.setVehicle(vehicle);
					close_ParkVehicleUseRecord.setType(com.tiamaes.bike.common.bean.integrated.Type.APP);
					kafkaTemplate.send(MessageBuilder.withPayload(close_ParkVehicleUseRecord)
							.setHeader(KafkaHeaders.TOPIC, close_ParkVehicleUseRecord.getClass().getName()).build());
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		return new Message8001(header, Result.SUCCESS);
	}

}
