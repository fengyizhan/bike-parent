package com.tiamaes.bike.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码工具类
 * @author chen
 *
 */
public final class MobileUtils {
	
	private static final String TEST = "18983698404";
	/**
	 * <p>手机号正则表达式</p>
	 */
	private static final String MOBILE_PATTERN = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
	/**
	 * <p>中国移动手机号正则表达式</p>
	 */
	private static final String CHINAMOBILE_PATTERN = "^(13[4-9]{1}[0-9]{8})|(147[0-9]{8})|(15[0-2,7-9]{1}[0-9]{8})|(178{1}[0-9]{8})|(18[2,3,4,7,8]{1}[0-9]{8})$";
	/**
	 * <p>中国联通手机号正则表达式</p>
	 */
	private static final String UNICOM_PATTERN = "^(13[0-2]{1}[0-9]{8})|(145[0-9]{8})|(156[0-9]{8})|(18[5-6]{1}[0-9]{8})$";
	/**
	 * <p>中国电信手机号正则表达式</p>
	 */
	private static final String TELECOM_PATTERN = "^(133[0-9]{8})|(153[0-9]{8})|(180[0-9]{8})|(189[0-9]{8})$";
	
	
	/**
	 * <p>判断是否是正确的手机号码</p>
	 * @param mobile
	 * @return
	 */
	public static boolean isValidMobileNum(String mobile){
		Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
	}
	/**
	 * <p>判断是否为中国移动手机号码</p>
	 * @param mobile
	 * @return
	 */
	public static boolean isChinaMobileNum(String mobile){
		Pattern pattern = Pattern.compile(CHINAMOBILE_PATTERN);
		Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
	}
	
	/**
	 * <p>判断是否为中国联通手机号</p>
	 */
	public static boolean isChinaUnicomNum(String mobile){
		Pattern pattern = Pattern.compile(UNICOM_PATTERN);
		Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
	}
	
	/**
	 * <p>判断是否为中国电信手机号</p>
	 */
	public static boolean isChinaTelecomNum(String mobile){
		Pattern pattern = Pattern.compile(TELECOM_PATTERN);
		Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
	}
	
	/**
	 * <p>生成指定位数的随机数字，默认为6位</p>
	 */
	public static String buildRandomNum(int len){
		StringBuffer random = new StringBuffer(len);
		for(int i = 0; i < len; i++){
			random.append((int)(Math.random() * 10));
		}
		return random.toString();
	}
    /**
     * 将手机号隐藏中间四位
     * @param msisdn 源手机号
     * @return 隐藏过中间四位后的手机号
     */
    public static String hideMsisdn(String msisdn){
    	return msisdn.substring(0,3) + "****" + msisdn.substring(7);
    }
	public static void main(String[] args) {
		System.out.println(MobileUtils.isValidMobileNum(TEST));
		System.out.println(MobileUtils.isChinaMobileNum(TEST));
		System.out.println(MobileUtils.isChinaUnicomNum(TEST));
		System.out.println(MobileUtils.isChinaTelecomNum(TEST));
		System.out.println(MobileUtils.buildRandomNum(6));
	}

}
