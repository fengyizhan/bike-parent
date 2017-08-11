package com.tiamaes.bike.api.util;

import java.util.Random;

/**
 * 系统工具类
 * @author fyz
 */
public class SystemUtil {
	/**
	 * 生成指定位数的随机数
	 * @param num 位数限定
	 * @return 随机数
	 */
	public static int numberGenerator(int num)
	{
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < num; i++)
		{
			result = result * 10 + array[i];
		}
		return result;
	}
	public static void main(String args[])
	{
		int number4=SystemUtil.numberGenerator(4);
		int number6=SystemUtil.numberGenerator(6);
		System.out.println(number4);
		System.out.println(number6);
	}
}
