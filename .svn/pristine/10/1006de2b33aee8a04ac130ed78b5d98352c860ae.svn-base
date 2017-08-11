package com.tiamaes.bike.messages;


import org.springframework.beans.factory.annotation.Autowired;

import com.tiamaes.bike.config.SystemConfig.JpushProperties;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送
 */
public class MPushPayLoad {
	@Autowired
	private JpushProperties jpushProperties;
	public static JPushClient jpushClient ;
	public JPushClient instance(){
		if(jpushClient==null){
			String appKey=jpushProperties.getAppKey();
			String masterSecret=jpushProperties.getMasterSecret();
			ClientConfig config= ClientConfig.getInstance();
			config.setMaxRetryTimes(3);
			jpushClient=new JPushClient(masterSecret,appKey,null, config);
			//jpushClient = new JPushClient(masterSecret, appKey, 3);
		}
		return jpushClient;
	}
	/**
	 * @return
	 * 全部推送,只有内容
	 */
	public PushPayload SENDALL(String msg) {
		   return PushPayload.alertAll(msg);
	}
	/**
	 *定点推动，只有内容
	 */
	public PushPayload SENDPOINT(String msg,String alias){
		  return PushPayload.newBuilder()
          .setPlatform(Platform.all())
          .setAudience(Audience.alias(alias))
          .setNotification(Notification.alert(msg))
          .build();
	}
	/**
	 * @param msg
	 * @param alias
	 * @return
	 * 定点推送，只有消息
	 */
	public PushPayload SENDPOINT_MSG(String msg,String alias){
		  return PushPayload.newBuilder()
        .setPlatform(Platform.all())
        .setAudience(Audience.alias(alias))
        .setMessage(Message.content(msg)) 
        .build();
	}
}
