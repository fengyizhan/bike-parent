package com.tiamaes.bike.messages;

import org.springframework.stereotype.Component;

/**
 * @author fyz 消息推送
 */
@Component("pushUtil")
public class Push extends MPushPayLoad{
	/**
	 * 群发通知
	 */
	public void send(String msg) {
		try{
			instance().sendPush(SENDALL(msg));
		}catch(Exception e){
			//e.printStackTrace();
		} 
	}
	 
	/**
	 * 定点发通知
	 */
	public String  sendNotice(String msg,String alias){
		String sendLog="发送定点通知成功！";
		try{
			instance().sendPush(SENDPOINT(msg,alias));  
		}catch(Exception e){
			sendLog=e.toString();
		}
		return sendLog;
	} 
	/**
	 *定点发消息
	 */
	public String sendMsg(String msg,String alias){
		String sendLog="发送消息成功！";
		try{
			instance().sendPush(SENDPOINT_MSG(msg,alias));
		}catch(Exception e){
			sendLog=e.toString();
		}
		return sendLog;
	}
	
}
