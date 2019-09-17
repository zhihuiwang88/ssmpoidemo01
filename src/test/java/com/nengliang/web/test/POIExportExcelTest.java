package com.nengliang.web.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class POIExportExcelTest {

	
	/**
	*
	*使用POI的HSSF创建Excel表格, aa.xls
	*使用POI的XSSF创建Excel表格, bb.xlsx,支持office 2007+
	*如何创建新工作簿
	*如何创建工作表
	*如何创建单元格
	*迭代行和单元格
	*获取单元格内容
	 * @throws IOException 
	*
	*
	*/

	// @Test
	public void createExcelHSSF() throws IOException {

		String[] title = { "id", "name", "age" };
		// 创建工作簿
		Workbook workbook = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet();
		// 创建第一行，设置表头
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 创建第二行，单元格追加数据
		for (int i = 1; i <= 10; i++) {
			HSSFRow nextRow = sheet.createRow(i);
			// 第二行的单元格
			HSSFCell nextCell = nextRow.createCell(0);
			// id
			nextCell.setCellValue("" + i);
			// name
			nextCell = nextRow.createCell(1);
			nextCell.setCellValue("李" + i);
			// age
			nextCell = nextRow.createCell(2);
			nextCell.setCellValue("1" + i);

		}
		// 创建一个文件,将内容写入到磁盘
		String pathname = "E:\\aaa.xls";
		File file = new File(pathname);
		FileOutputStream fos = FileUtils.openOutputStream(file);
		workbook.write(fos);
		fos.close();
	}

	 //@Test
	public void createExcelXSSF() throws IOException {

		String[] title = { "id", "name", "age" };
		// 创建工作簿
		Workbook wb = new XSSFWorkbook();
		// 创建工作表
		Sheet sheet = wb.createSheet();
		// 创建第一行，设置表头
		Row row = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		// 创建第二行，单元格追加数据
		for (int i = 1; i <= 10; i++) {
			Row nextRow = sheet.createRow(i);
			// 第二行的单元格
			Cell nextCell = nextRow.createCell(0);
			// id
			nextCell.setCellValue("" + i);
			// name
			nextCell = nextRow.createCell(1);
			nextCell.setCellValue("李" + i);
			// age
			nextCell = nextRow.createCell(2);
			nextCell.setCellValue("1" + i);

		}
		// 创建一个文件,将内容写入到磁盘
		String pathname = "E:\\bb.xlsx";
		File file = new File(pathname);
		FileOutputStream fos = FileUtils.openOutputStream(file);
		wb.write(fos);
		fos.close();
	}
	
	
// 最后一个	
}
