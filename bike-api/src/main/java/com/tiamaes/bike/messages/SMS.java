package com.tiamaes.bike.messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tiamaes.bike.config.SystemConfig.SmsProperties;
 

/**
 * 发短信工具类
 */
@Component("smsUtil")
public class SMS {
	protected static final String Server = "http://www.139000.com"; 
	@Autowired
	private SmsProperties smsProperties;
	/**
	 * @param dst	群发目标手机号(逗号分隔)
	 * @param msg	发送短信内容 
	 * @return 		短信服务商回执信息
	 */
	public String send(String dst, String msg) { 
		String sUrl = null;
		try {
			sUrl = Server + "/send/gsend.asp?name=" + smsProperties.getName() + "&pwd="
					+ smsProperties.getPassword() + "&dst=" + dst + "&msg="
					+ URLEncoder.encode(msg, "GBK") ; 
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		} 
		return getCode(sUrl);
	}

	 
	/**
	 * @param urlString
	 * @return
	 * 获取发送状态
	 */
	public String getCode(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"gb2312"));
			
			for (String line = null; (line = reader.readLine()) != null;)
			{
				sb.append(line + "\n");
			}
			reader.close(); 
		} catch (IOException e) {
			System.out.println(e.toString());
		}  
		return sb.toString();
	}
	/**
	 * 获取6位随机码
	 */
	public String getNoteCode(){
		int a[] = new int[10];
	 	String code="";
        for (int i = 0; i <= 6; i++) 
        {
            a[i] = (int) (Math.random() * 10); 
        }
        for (int i = 0; i < 6;)
        {   
        	code+=a[i++];
        }
        return code;
	}
 
}
