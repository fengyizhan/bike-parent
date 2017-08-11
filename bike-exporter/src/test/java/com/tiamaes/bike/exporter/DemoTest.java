package com.tiamaes.bike.exporter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiamaes.bike.utils.ExcelWorkBook;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
	private static Logger logger = LogManager.getLogger(DemoTest.class);
	
	@Value("${java.io.tmpdir}")
	private String tempdir;
	
	@Before
	public void before() {
	}
	
	@Test
	public void test() throws Exception {
//		File biosampleFile = ResourceUtils.getFile("classpath:/static/excelmodel/车辆报警记录模板-导出.xlsx");
//		logger.debug(biosampleFile);
		long start = System.currentTimeMillis();
		List<Object> body = new ArrayList<Object>();
		for(int i = 0; i < 100 * 10000; i++){
			body.add(new Object());
		}
		final String[] headers = { "header1", "header2", "header3", "header4", "header5"};
		SXSSFWorkbook excel = new ExcelWorkBook<Object>(headers, body){
			@Override
			protected void createExcelCells(Object record, Row row) {				 
				Cell cell = row.createCell(0);
				cell.setCellValue("1");
				Cell cell1 = row.createCell(1);
				cell1.setCellValue("2");
				Cell cell2 = row.createCell(2);
				cell2.setCellValue("3");
				Cell cell3 = row.createCell(3);
				cell3.setCellValue("4");
				Cell cell4 = row.createCell(4);		
				cell4.setCellValue("5");
			}
		};
		logger.debug(tempdir);
		FileOutputStream out = new FileOutputStream(tempdir + "/test.xlsx");
		excel.write(out);
		out.close();
		excel.dispose();
		logger.debug(String.format("cost : %d ms", System.currentTimeMillis() - start));
	}
	
	
	
	@Test
	@Ignore
	public void test2() throws Exception {
	}
	
}

