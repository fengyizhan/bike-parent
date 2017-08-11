package com.tiamaes.bike.utils;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public abstract class ExcelWorkBook<T> extends SXSSFWorkbook{
	
	public ExcelWorkBook(final String[] headers, List<T> list) {
		super(1000);
		this.setCompressTempFiles(true);
        Sheet sheet = this.createSheet();
        createExcelHeader(headers, sheet);
        createExcelBody(list, sheet);
	}

	private void createExcelBody(List<T> list, Sheet sheet) {
		int i = 0;
        for(T t : list){
        	i++;
            Row row = sheet.createRow(i);
            createExcelCells(t, row);
        }
	}

	private void createExcelHeader(final String[] headers, Sheet sheet) {
		int i = 0;
        Row head = sheet.createRow(0);
        for(String header: headers){
        	Cell cell = head.createCell(i);
        	cell.setCellValue(header);
        	i++;
        }
	}
	
	protected abstract void createExcelCells(T t,Row row);
}
