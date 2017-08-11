package com.tiamaes.bike.connector.protocol.handler.command;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.command.UnlockCommand;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.config.ChannelRepository;
import com.tiamaes.bike.connector.DriverServiceInterface;
import com.tiamaes.bike.connector.ParkServiceInterface;
import com.tiamaes.bike.connector.VehicleServiceInterface;
import com.tiamaes.bike.connector.protocol.message.Message8012;
import com.tiamaes.security.core.userdetails.User;

import io.netty.channel.Channel;

/**
 * 解锁命令
 * @author Chen
 *
 */
@Component
public class UnlockCommandHandler implements CommandHandler<UnlockCommand> {
	
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
	@Override
	public void execute(UnlockCommand command) throws Exception {
		UnlockCommand.Control control=command.getControl();
		Vehicle vehicle=null;
		User driver=null;
		/**
		 * 扫码所在【停车区】
		 */
		String parkId="";
		/**
		 * 当前使用的【车辆】
		 */
		String vehicleId="";
		/**
		 * 解锁业务
		 */
		if(UnlockCommand.Control.UNLOCK.equals(control))
		{
			String driverId=command.getDriverId();
			vehicleId=command.getVehicleId();
			vehicle=vehicleService.getVehicleById(vehicleId);
			parkId=vehicle.getParentId();
			if(parkId==null)
			{
				Park currentPark=vehicle.getPark();
				if(currentPark!=null)
				{
					parkId=currentPark.getId();
				}
			}
			driver=driverService.getDriverByUsername(driverId);
			/**
			 * 将当前车辆与【租借人】关联，便于后续逻辑处理
			 */
			vehicleUserRedisTemplate.opsForHash().put(RedisKey.VEHICLES_DRIVERS_VEHICLEID,vehicle.getId(),driver);
		}
		//TODO 为了演示
		if(StringUtils.isEmpty(parkId))
		{
			parkId="10006";
		}
		Message8012 message = new Message8012(command);
		/**
		 * TODO
		 * 如果在【非停车区】可以‘扫码开锁’，指令是下发给【锁】的，所以，channel应该取【锁】
		 * 如果在【停车区】可以‘扫码开锁’，指令是下发给【桩】的，所以，channel应该取【桩】
		 */
		Channel channel = null;
		if(StringUtils.isNotEmpty(parkId))
		{//说明在【桩】内
			channel=channelRepository.getStation(parkId);
		}else
		{//说明在【桩外】，与【锁】通讯
			channel=channelRepository.getLock(vehicleId);
		}
		if(channel!=null)
		{
			channel.writeAndFlush(message);
		}
	}
}
