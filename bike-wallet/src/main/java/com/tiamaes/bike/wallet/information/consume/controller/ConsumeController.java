package com.tiamaes.bike.wallet.information.consume.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.JPushContent;
import com.tiamaes.bike.common.bean.Result;
import com.tiamaes.bike.common.bean.information.Park;
import com.tiamaes.bike.common.bean.information.Vehicle;
import com.tiamaes.bike.common.bean.integrated.BorrowRecord;
import com.tiamaes.bike.common.bean.system.PointVector;
import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.common.utils.LocationUtils;
import com.tiamaes.bike.wallet.information.MessageServiceInterface;
import com.tiamaes.bike.wallet.information.VehicleServiceInterface;
import com.tiamaes.bike.wallet.information.consume.service.ConsumeServiceInterface;
import com.tiamaes.bike.wallet.information.walletUser.service.WalletUserServiceInterface;
import com.tiamaes.mybatis.PageInfo;
import com.tiamaes.mybatis.Pagination;
import com.tiamaes.security.core.annotation.CurrentUser;
import com.tiamaes.security.core.userdetails.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/information/consume")
@Api(tags = {"consume"}, description = "用户消费信息管理")
public class ConsumeController {
	
	private static Logger logger = LogManager.getLogger(ConsumeController.class);
	@Resource
	private ConsumeServiceInterface consumeService;
	@Resource
	private WalletUserServiceInterface walletUserService;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String,BorrowRecord> borrowRecordRedisTemplate;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String,Vehicle> vehicleRedisTemplate;
	@Resource
	private VehicleServiceInterface vehicleService;
	@Resource
	private MessageServiceInterface messageService;
	@Autowired
	private ObjectMapper objectMapper;
	@Resource(name = "jsonRedisTemplate")
	private RedisTemplate<String,User> vehicleUserRedisTemplate;
	/**
	 * 根据用户名获取用户消费信息
	 * @param username
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "用户消费信息", notes = "根据用户名获取用户消费信息")
	@RequestMapping(value = {"/page/{number:\\d+}","/page/{number:\\d+}/{pageSize:\\d+}"}, method = RequestMethod.POST)
	public @ResponseBody PageInfo<Consume> get(@RequestBody Consume consume, @PathVariable Map<String,String> pathVariable, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){
			logger.debug(consume.getUsername());
		}
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 10 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<Consume> pagination = new Pagination<>(number, pageSize);
		List<Consume> consumes = consumeService.getListOfConsumesByUsername(consume, pagination);
		return new PageInfo<Consume>(consumes);
	}
	
	/**
	 * 新增用户消费信息
	 * @param consume
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "新增用户消费信息", notes = "新增用户消费信息",response = Consume.class)
	@ApiImplicitParam(name = "consume", value = "用户消费实体", required = true, dataType = "Consume")
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Consume add(@RequestBody Consume consume, @CurrentUser User operator){
		if(logger.isDebugEnabled()){
			logger.debug(consume.toString());
		}
		consume.setId(consumeService.getId());
		consume.setCreateTime(new Date());
		consume = consumeService.addConsume(consume);
		return consume;
	}
	
	/**
	 * 更新用户消费信息
	 * @param consume
	 * @param username
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "更新用户消费信息", notes = "更新用户消费信息",response = Consume.class)
	@ApiImplicitParam(name = "consume", value = "用户消费实体", required = true, dataType = "Consume")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody Consume update(@RequestBody Consume consume, @PathVariable("id")Integer id, @CurrentUser User operator) {
		if(logger.isDebugEnabled()){	
			logger.debug(consume.toString());
		}
		consume.setId(id);
		consume = consumeService.updateConsume(consume);
		return consume;
	}
	/**
	 * 根据用户名获取用户消费信息
	 * @param username
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "用户消费信息", notes = "根据用户名获取用户消费信息")
	@RequestMapping(value = {"/userlist/page/{number:\\d+}","/userlist/page/{number:\\d+}/{pageSize:\\d+}"}, method = RequestMethod.POST)
	public @ResponseBody PageInfo<Consume> userlist(@PathVariable Map<String,String> pathVariable, @CurrentUser User operator) {
		Assert.notNull(operator,"用户登陆信息不正确！");
		Consume consume=new Consume();
		consume.setUsername(operator.getUsername());
		int number = pathVariable.get("number") == null ? 1 : Integer.parseInt(pathVariable.get("number"));
		int pageSize = pathVariable.get("pageSize") == null ? 10 : Integer.parseInt(pathVariable.get("pageSize"));
		Pagination<Consume> pagination = new Pagination<>(number, pageSize);
		List<Consume> consumes = consumeService.getListOfConsumesByUsername(consume, pagination);
		return new PageInfo<Consume>(consumes);
	}
	/**
	 * APP用户锁车结账时新增用户消费信息
	 * @param consume
	 * @param operator
	 * @return
	 */
	@ApiOperation(value = "APP用户锁车结账时新增用户消费信息", notes = "APP用户锁车结账时新增用户消费信息",response = Consume.class)
	@ApiImplicitParam(name = "consume", value = "用户消费实体", required = true, dataType = "Consume")
	@RequestMapping(value = "/userConsume",method = RequestMethod.POST)
	public @ResponseBody Result addConsume(@RequestBody Consume consume, @CurrentUser User operator){
		if(logger.isDebugEnabled()){
			logger.debug(consume.toString());
		}
		String username=consume.getUsername();
		Assert.notNull(username,"用户信息不正确！");
		Result result=new Result();
		result.setSuccess(true);
		Map<String, Object> dataMap=result.getData();
		//检查当前人员是否已经租借一辆车
		BorrowRecord borrowRecord=(BorrowRecord)borrowRecordRedisTemplate.opsForHash().get(RedisKey.DRIVERS_BORROW_RECORDS,username);
		if(borrowRecord==null)
		{
			result.setSuccess(false);
			result.setMessage("用户已经归还租借车辆，无法继续操作！");
			return result;
		}
		try {
			/**
			 * TODO 由于锁是手动上锁，所以逻辑触发应由【锁设备】直接触发
			 */
			String vehicleId=borrowRecord.getVehicle().getId();
			Vehicle vehicle = (Vehicle)vehicleRedisTemplate.opsForHash().get(RedisKey.VEHICLES,vehicleId);
			if(vehicle==null)
			{
				result.setSuccess(false);
				result.setMessage("用户租借的【车辆信息】加载异常，无法操作！");
				return result;
			}
			//当前车辆最新位置状态是在【车辆关锁】时的【停车区】内，代表‘还车位置’
			Park endPark=vehicle.getPark();
			//车辆借出时位置状态是在【车辆开锁】时的【停车区】内，代表‘租车位置’
			Park startPark=borrowRecord.getVehicle().getPark();
			borrowRecord.setEndPark(endPark);
			Date endTime=new Date();
			Date startTime=borrowRecord.getStartTime();
			/**
			 * 自行车使用时长、距离等
			 */
			long usedTime=endTime.getTime()-startTime.getTime();//使用时长：毫秒值
			borrowRecord.setCountTime(String.valueOf(usedTime));
			double usedMeters=LocationUtils.getDistance(startPark.getLat(),startPark.getLng(),endPark.getLat(),endPark.getLng());//骑行距离：米
			borrowRecord.setEndTime(endTime);
			borrowRecord.setKilometers(usedMeters);
			/**
			 * TODO 开始计算费用，由于【费用计算规则、计算公式】尚不明确，此处暂时按此处理
			 */
			borrowRecord.setCost(1);
			//锁车之后，车辆状态要更新为【可借出】状态
			PointVector.Info vehicleInfo=vehicle.getInfo();
			if(vehicleInfo!=null)
			{
				vehicleInfo.setRunState(PointVector.Info.RunState.CANBORROW);
			}
			vehicleService.updateVehicle(vehicle, vehicle.getId());
			//检查当前人员是否已经租借一辆车【骑行中】的标志位，租车流程结束后要清空
			borrowRecordRedisTemplate.opsForHash().delete(RedisKey.DRIVERS_BORROW_RECORDS,username);
			vehicleUserRedisTemplate.opsForHash().delete(RedisKey.VEHICLES_DRIVERS_VEHICLEID,vehicle.getId());
			/**
			 * 生成消费记录
			 */
			consume.setId(consumeService.getId());
			consume.setCreateTime(new Date());
			walletUserService.updateWalletUserByPayConsume(consume);
			dataMap.put("result",consume);
			/**
			 * 发送【租借行程】记录，供APP使用
			 */
			JPushContent pushContent=new JPushContent();
			pushContent.setMobiles(username);
			Result result_consume=new Result();
			result_consume.setSuccess(true);
			result_consume.setMessage("还车消费结束");
			result_consume.setType(Result.JSON_TYPE);
			result_consume.getData().put("borrow",borrowRecord);
			pushContent.setContent(objectMapper.writeValueAsString(result_consume));
			logger.info("租车记录："+pushContent.getContent());
			Result pushResult=messageService.sendJpush(pushContent);
			String pushLog=pushResult.getMessage();
			logger.info("还车消费，极光推送："+pushLog);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("消费扣款异常！");
		}
		return result;
	}
	
}
