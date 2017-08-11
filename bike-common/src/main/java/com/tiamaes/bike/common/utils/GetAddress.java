package com.tiamaes.bike.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAddress {
	
	/**
	 * 根据经纬度生成详细地址(百度)
	 * @param lat
	 * @param lng
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String  generateAddress(String lat, String lng) {
		String addr = "";
		String url = "http://api.map.baidu.com/geocoder/v2/";
		String params = "ak=DwFAUTwtxbHyh0POXUr1deglTP0vTMEZ&location=%s,%s&output=json&pois=0&coordtype=wgs84ll";
		// 发送 GET 请求
		String rspcontent,business;
		Map<String,Object>   pois ;		
		try {
			rspcontent = HttpRequest.sendGet(url, String.format(params, lat, lng));
			if (StringUtils.isNotBlank(rspcontent)) {
				ObjectMapper mapper = new ObjectMapper();
			
					Map<String, Object> g = mapper.readValue(rspcontent, Map.class);
					if (null != g.get("status") && g.get("status").toString().equals("0")) {
						Map<String, Object> g2 = (Map<String, Object>) g.get("result");
						addr = (String) g2.get("formatted_address");					
						business = (String) g2.get("business");	
						pois = (Map<String,Object>) g2.get("pois");
						if (!"".equals(business)){
							addr=addr+" , "+business;
						};
						if (pois != null){							
							addr=addr+" , "+ pois.get("name")+" , "+pois.get("poiType ");
						};	
						addr=addr+"附近";
					};				
			};
		} catch (Exception e) {
    		
		};
		return addr;
	}
	
	/**
	 * 根据经纬度生成详细地址 谷歌
	 * @param lat
	 * @param lng
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String  generateGoogleAddress(String lat, String lng) {
		int  len ;
		String addr = null ;
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
		String params = "location=34.6898,113.68626666666667&radius=500&types=locality|political|sublocality|establishment|point_of_interest&key=AIzaSyDovT5Q9r118AXmZfYcE3vVh3aODWRxZkA";
		// 发送 GET 请求
		String rspcontent = HttpRequest.sendGet(url, params);
		if (StringUtils.isNotBlank(rspcontent)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, Object> g = mapper.readValue(rspcontent, Map.class);
				List<Object> results = null;
				@SuppressWarnings("unused")
				Map<String, Object> place = null;
				if ("OK".equals(g.get("status"))) {
					results = (List<Object>)g.get("results");	
					len = results.size();					
					for (int i = 0; i < len; i++){
						place = (Map<String, Object>)results.get(i);
						//placeId = (String) place.get("place_id"); 
					};				
				};
			} catch (IOException e) {
//						e.printStackTrace();
			};
		};
		return addr;
	}
	
	/**
	 * 根据详细地址生成经纬度 百度
	 * @param addr
	 * @return
	 */
	public static Map<String, BigDecimal> getLatAndLngByAddress(String addr){
        String lat = "";
        String lng = "";
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
        +"ak=DwFAUTwtxbHyh0POXUr1deglTP0vTMEZ&output=json&address=%s",addr);
        URL myURL = null;
        URLConnection httpsConn = null;  
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {

        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    lat = data.substring(data.indexOf("\"lat\":") 
                    + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":") 
                    + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
                insr.close();
            }
        } catch (IOException e) {
        	
        }
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("lat", new BigDecimal(lat));
        map.put("lng", new BigDecimal(lng));
        return map;
	}
	
	public static void main(String[] args) {
		Map<String, BigDecimal> map = GetAddress.getLatAndLngByAddress("杭州市长河路");
        System.out.println("经度："+map.get("lng")+"---纬度："+map.get("lat"));
	}
	
}
