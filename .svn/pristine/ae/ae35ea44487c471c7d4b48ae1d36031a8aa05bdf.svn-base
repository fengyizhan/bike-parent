package com.tiamaes.bike.exporter.integrated.pile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiamaes.bike.common.bean.integrated.PileRecord;
import com.tiamaes.bike.common.utils.UUIDGenerator;
import com.tiamaes.bike.common.utils.ZipUtils;
import com.tiamaes.bike.exporter.integrated.pile.persistence.PileQueryMapper;
import com.tiamaes.bike.utils.ExcelWorkBook;
import com.tiamaes.mybatis.PageHelper;
import com.tiamaes.mybatis.Pagination;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PileQueryService implements PileQueryServiceInterface {
	private static Logger logger = LogManager.getLogger(PileQueryService.class);
	
	@Value("${java.io.tmpdir}")
	private String tmpdir;// 本地临时文件目录
	
	@Resource
	private PileQueryMapper pileQueryMapper;
	
	@Override
	public File exportExcelOfPileRecords(PileRecord pileRecord) throws Exception {
		String fileName = pileRecord.getZipFileName();
		final String[] headers = { "停放区编号", "报警类型", "报警时间", "报警地点"};
		long start = System.currentTimeMillis();
		List<PileRecord> body = new ArrayList<PileRecord>(60000);
		List<File> files = new ArrayList<File>();
		int page = 1;
		while (true) {
			Pagination<PileRecord> pagination = new Pagination<PileRecord>(page, 60000);
			PageHelper.startPage(pagination);
			body = pileQueryMapper.getListOfPileRecords(pileRecord);
			if (body.size() == 0) {
				break;
			}
			SXSSFWorkbook excel = new ExcelWorkBook<PileRecord>(headers, body){
				@Override
				protected void createExcelCells(PileRecord record, Row row) {
					Cell cell = row.createCell(0);
					cell.setCellValue(record.getPark().getName());
					Cell cell1 = row.createCell(1);
					cell1.setCellValue(record.getWarnCode().getName());
					Cell cell2 = row.createCell(2);
					cell2.setCellValue(DateFormatUtils.format(record.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
					Cell cell3 = row.createCell(3);
					cell3.setCellValue(record.getPlace());
				}
			};
			File xlsxFile = new File(tmpdir + "/" + UUIDGenerator.getUUID() + ".xlsx");
			FileOutputStream out = new FileOutputStream(xlsxFile);
			excel.write(out);
			out.close();
			excel.dispose();
			files.add(xlsxFile);
			if (body.size() < 60000) {
				break;
			} else {
				page++;
			}
		}
		// 进行打包工作
		String zipFileFullName = tmpdir + "/" + fileName;
		File zipFile = new File(zipFileFullName);
		ZipUtils.createZip(zipFile, files);
		logger.debug(String.format("cost : %d ms", System.currentTimeMillis() - start));
		return zipFile;
	}

}
