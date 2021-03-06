package com.tiamaes.bike.wallet.information.walletUser.service;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tiamaes.bike.common.RedisKey;
import com.tiamaes.bike.common.bean.wallet.Consume;
import com.tiamaes.bike.common.bean.wallet.Deposit;
import com.tiamaes.bike.common.bean.wallet.Recharge;
import com.tiamaes.bike.common.bean.wallet.WalletUser;
import com.tiamaes.bike.wallet.information.consume.persistence.ConsumeMapper;
import com.tiamaes.bike.wallet.information.consume.service.ConsumeService;
import com.tiamaes.bike.wallet.information.deposit.persistence.DepositMapper;
import com.tiamaes.bike.wallet.information.recharge.persistence.RechargeMapper;
import com.tiamaes.bike.wallet.information.walletUser.persistence.WalletUserMapper;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class WalletUserService implements WalletUserServiceInterface {
	
	@Resource
	private WalletUserMapper walletUserMapper;
	@Resource
	private RechargeMapper rechargeMapper;
	@Resource
	private DepositMapper depositMapper;
	@Resource
	private ConsumeMapper consumeMapper;
	@Resource(name = "jsonRedisTemplate")
	private HashOperations<String, String, WalletUser> operator;

	@Override
	public WalletUser getWalletUserByUsername(String username) {
		Assert.notNull(username, "用户名不能为空!");
		WalletUser walletUser = operator.get(RedisKey.WALLETUSERS, username); 
		if (walletUser == null) {
			walletUser = walletUserMapper.getWalletUserByUsername(username);
			if (walletUser != null) {
				operator.putIfAbsent(RedisKey.WALLETUSERS, username, walletUser);
			}
		}
		return walletUser;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public WalletUser addWalletUser(WalletUser walletUser) {
		Assert.notNull(walletUser);
		WalletUser existed=walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if(existed==null)
		{
			walletUserMapper.addWalletUser(walletUser);
		}
		WalletUser result = walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if (result != null) {
			operator.putIfAbsent(RedisKey.WALLETUSERS, walletUser.getUsername(), walletUser);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized WalletUser updateWalletUser(WalletUser walletUser) {
		Assert.notNull(walletUser);
		walletUserMapper.updateWalletUser(walletUser);
		WalletUser result = walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if (result != null) {
			operator.put(RedisKey.WALLETUSERS, walletUser.getUsername(), walletUser);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteWalletUser(WalletUser walletUser) {
		Assert.notNull(walletUser, "用户钱包信息不能为空!");
		walletUserMapper.deleteWalletUser(walletUser);
		WalletUser result = walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if (result == null) {
			operator.delete(RedisKey.WALLETUSERS, walletUser.getUsername());
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized boolean updateWalletUserByPayRecharge(Recharge recharge) {
		Assert.notNull(recharge,"充值信息不能为空！");
//		float money=recharge.getMoney();
		String username=recharge.getUsername();
		Assert.notNull(username,"用户信息不能为空！");
		//1.先保存充值明细
		rechargeMapper.addRecharge(recharge);
		
		//2.加载【用户钱包信息】
		WalletUser walletUser=walletUserMapper.getWalletUserByUsername(username);
		if(walletUser==null)
		{//如果用户对应钱包信息不存在，先生成一个
			walletUser=new WalletUser();
			walletUser.setUsername(username);
			walletUser.setBalance(0);
			walletUserMapper.addWalletUser(walletUser);
		}
		synchronized(walletUser)
		{//更新钱包总金额、更新已添加的充值明细【总金额】冗余数据
			float currentBalance=walletUser.getBalance();
			float rechargeMoney=recharge.getMoney();
			float finalBalance=currentBalance+rechargeMoney;
			walletUser.setBalance(finalBalance);
			recharge.setBalance(finalBalance);
			rechargeMapper.updateRecharge(recharge);
			walletUserMapper.updateWalletUser(walletUser);
		}
		WalletUser result = walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if (result != null) {
			operator.put(RedisKey.WALLETUSERS, walletUser.getUsername(), walletUser);
		}
		return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized boolean updateWalletUserByPayConsume(Consume consume) {
		Assert.notNull(consume,"充值信息不能为空！");
//		float money=consume.getMoney();
		String username=consume.getUsername();
		Assert.notNull(username,"用户信息不能为空！");
		//1.先保存充值明细
		consume.setId(consumeMapper.getId());
		consumeMapper.addConsume(consume);
		//2.加载【用户钱包信息】
		WalletUser walletUser=walletUserMapper.getWalletUserByUsername(username);
		if(walletUser==null)
		{//如果用户对应钱包信息不存在，先生成一个
			walletUser=new WalletUser();
			walletUser.setUsername(username);
			walletUser.setBalance(0);
			walletUserMapper.addWalletUser(walletUser);
		}
		synchronized(walletUser)
		{//更新钱包总金额、更新已添加的充值明细【总金额】冗余数据
			float currentBalance=walletUser.getBalance();
			float useMoney=consume.getMoney();
			float finalBalance=currentBalance-useMoney;
			walletUser.setBalance(finalBalance);
			walletUserMapper.updateWalletUser(walletUser);
		}
		WalletUser result = walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if (result != null) {
			operator.put(RedisKey.WALLETUSERS, walletUser.getUsername(), walletUser);
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public synchronized boolean updateWalletUserByDeposit(Deposit deposit) {
		Assert.notNull(deposit,"押金信息不能为空！");
		Deposit.Type type=deposit.getType();
//		float money=deposit.getDeposit();
		String username=deposit.getUsername();
		Assert.notNull(username,"用户信息不能为空！");
		//1.先保存押金明细
		depositMapper.addDeposit(deposit);
		//2.加载【用户钱包信息】
		WalletUser walletUser=walletUserMapper.getWalletUserByUsername(username);
		if(walletUser==null)
		{//如果用户对应钱包信息不存在，先生成一个
			walletUser=new WalletUser();
			walletUser.setUsername(username);
			walletUser.setBalance(0);
			walletUserMapper.addWalletUser(walletUser);
		}
		synchronized(walletUser)
		{//更新钱包总金额、更新已添加的充值明细【总金额】冗余数据
			if(type.equals(Deposit.Type.CHARGE))
			{//如果是扣款
				float currentBalance=walletUser.getDeposit();
				float useMoney=deposit.getDeposit();
				float finalBalance=currentBalance-useMoney;
				walletUser.setDeposit(finalBalance);
			}
			if(type.equals(Deposit.Type.REFUND))
			{//如果是退款
				float currentBalance=walletUser.getDeposit();
				float useMoney=deposit.getDeposit();
				float finalBalance=currentBalance-useMoney;
				walletUser.setDeposit(finalBalance);
			}
			if(type.equals(Deposit.Type.RECHARGE))
			{//如果是充值
				float currentBalance=walletUser.getDeposit();
				float useMoney=deposit.getDeposit();
				float finalBalance=currentBalance+useMoney;
				walletUser.setDeposit(finalBalance);
			}
			walletUserMapper.updateWalletUser(walletUser);
		}
		WalletUser result = walletUserMapper.getWalletUserByUsername(walletUser.getUsername());
		if (result != null) {
			operator.put(RedisKey.WALLETUSERS, walletUser.getUsername(), walletUser);
		}
		return true;
	}

}
