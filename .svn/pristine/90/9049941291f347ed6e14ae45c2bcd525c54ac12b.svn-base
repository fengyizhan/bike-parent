package com.tiamaes.bike.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZipUtils {
	
	private static Logger logger = LogManager.getLogger(ZipUtils.class);
	
	public static boolean createZip(File zipFile, List<File> files) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File xlsxFile : files) {
            	if (xlsxFile != null && xlsxFile.length() > 0) {
            		executeCompress(out, xlsxFile, xlsxFile.getName());
            	}
            }
            out.close();
            logger.info("compress mission accomplished");
            return true;
        } catch (Exception e) {
        	logger.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
	}
	
	private static void executeCompress(ZipOutputStream out, File f, String base) {
		try {
			logger.info(base);
			out.putNextEntry(new ZipEntry(base)); // 创建zip实体
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(in);
			int length;
			while ((length = bis.read()) != -1) {
				out.write(length);
			}
			out.closeEntry();
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) {
		try {
			File zipFile = new File("D:/" + UUIDGenerator.getUUID() + ".zip");
			List<File> files = new ArrayList<>();
			files.add(new File("D:/1.xlsx"));
			files.add(new File("D:/2.xlsx"));
			createZip(zipFile, files);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
