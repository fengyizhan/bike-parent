package com.tiamaes.bike.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

/**
 * 图片工具类
 * @author waibao001
 *
 */
public class PicUtils {
	
	/**
	 * 图片转换成byte数组
	 * @param path 图片的路径
	 * @return byte[]
	 */
	public static byte[] image2byte(String path){
	    byte[] data = null;
	    FileImageInputStream input = null;
	    try {
		      input = new FileImageInputStream(new File(path));
		      ByteArrayOutputStream output = new ByteArrayOutputStream();
		      byte[] buf = new byte[1024];
		      int numBytesRead = 0;
		      while ((numBytesRead = input.read(buf)) != -1) {
		    	  output.write(buf, 0, numBytesRead);
		      }
		      data = output.toByteArray();
		      output.close();
		      input.close();
	    }catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    }
	    catch (IOException ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	 }
	
	/**
	 * byte数组到图片
	 * @param data 
	 * @param path 生成的图片路径
	 */
	public static void byte2image(byte[] data,String path){
		if(data.length<3||path.equals("")) return;
		    try{
			    FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			    imageOutput.write(data, 0, data.length);
			    imageOutput.close();
		    } catch(Exception ex) {
		    	ex.printStackTrace();
		    }
	  }
}
