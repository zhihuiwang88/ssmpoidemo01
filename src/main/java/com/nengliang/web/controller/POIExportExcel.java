package com.nengliang.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nengliang.web.entity.Scholastic;
import com.nengliang.web.service.ScholasticService;
import com.nengliang.web.util.JsonResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




@Controller
@RequestMapping("/scholastic")
public class POIExportExcel {

	//private static final Logger logger = LoggerFactory.getLogger(POIExportExcel.class);
	
	@Autowired
	private ScholasticService  schService;
	
	/**
	 *  https://blog.csdn.net/l18848956739/article/details/82702962
	*接受参数有：Excel的表头和Excel的数据
	*使用POI的HSSF创建Excel表格, aa.xls
	*使用POI的XSSF创建Excel表格, bb.xlsx,支持office 2007+
	*如何创建新工作簿
	*如何创建工作表
	*如何创建单元格
	*迭代行和单元格
	*获取单元格内容
	 * @throws IOException 
	*  http://localhost:8081/ssmupload/scholastic/listAll
	* Scholastic
	*/

	@RequestMapping(value="/exportExcel",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult exportExcel(@RequestBody String obj) throws Exception {
		
		/*
		 * json字符串是这样的  "[{"id":12,"name":"李四"},{"id":12,"name":"张三"}]"
		 * 自适应宽高
		 * 文字居中
		 * 时间格式转换
		 *  https://www.jb51.net/article/53440.htm
		 * 
		 */
		
		
		// json字符串转json数组
		JSONArray jsonArray = JSONArray.fromObject(obj);
        // 设置固定表头	username 	    creationtime
		String[] title = {"id","姓名","userage","usergender","mailbox","headportrait","创建时间","modifytime"};
		// 创建工作簿
		Workbook workbook = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet sheet = (HSSFSheet) workbook.createSheet();
		
		// 设置字体大小及样式
		HSSFFont font = (HSSFFont) workbook.createFont();
		HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("宋体");
		// 设置字体居中
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(font);
		// 创建第一行，设置表头
		HSSFRow row  = sheet.createRow(0);
		// 设置表头字体居中
		row.setRowStyle(style);
		HSSFCell cell = null;
		for(int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			// 设置表头字体样式
			cell.setCellStyle(style);
		}
		// 创建第二行，单元格追加数据
		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject  jsonObject = jsonArray.getJSONObject(i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			HSSFRow nextRow = sheet.createRow(i + 1);
			
			// 第二行的单元格
			HSSFCell nextCell = nextRow.createCell(0);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(jsonObject.getString("id"));
			
			// name
			nextCell = nextRow.createCell(1);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(jsonObject.getString("username"));
			
			nextCell = nextRow.createCell(2);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(jsonObject.getString("userage"));
			
			nextCell = nextRow.createCell(3);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(jsonObject.getString("usergender"));
			
			nextCell = nextRow.createCell(4);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(jsonObject.getString("mailbox"));
			
			nextCell = nextRow.createCell(5);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(jsonObject.getString("headportrait"));
			// 字符串的时间转Long类型
			String string = jsonObject.getString("creationtime");
			long  longTime = Long.parseLong(string);
			Date dateCreat = new Date(longTime);
			nextCell = nextRow.createCell(6);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(sdf.format(dateCreat));
			
			String str = jsonObject.getString("modifytime");
			long lon = Long.parseLong(str);
			Date dateModify = new Date(lon);
			nextCell = nextRow.createCell(7);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(sdf.format(dateModify));
		}
		
		// 导出Excel数据前，设置中文列宽自适应
		for(int k = 0; k < title.length; k++) {
		     sheet.autoSizeColumn(k);
		     sheet.setColumnWidth(k, sheet.getColumnWidth(k) * 16 / 10);
		}
		
		
		JsonResult  jsonResult = new JsonResult();
		// 创建一个文件,将内容写入到磁盘
		String pathFile = "E:\\";
		String pathname = pathFile + "yonghu" + ".xls";
		File file = new File(pathname);
		
		try {
			FileOutputStream fos = FileUtils.openOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			jsonResult.setCode(100);
			jsonResult.setMsg("请关闭文件：" + pathname + " 后再进行数据导出！");
			return jsonResult;
		}
		
		
		jsonResult.setCode(200);
		jsonResult.setMsg("导出成功数据：" + jsonArray.size() + "条");
		return jsonResult;
	}
	
	/**
	 * 导出全部数据
	 * 创建工作薄
	 * 创建工作表
	 * 创建第一行，表头
	 * 创建第一行里的单元格
	 * 创建第二行
	 * 创建二行里的单元格
	 * 设置导出Excel的名字
	 * 进行导出数据
	 * 
	 *   http://localhost:8081/ssmupload/scholastic/listAll
	 */
	
	@RequestMapping(value="/exportExcelAll",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult exportExcelAll() throws Exception {
		// 查询书所有的数据
		List<Scholastic>  list = schService.selectParam();

        // 设置固定表头	username 	    creationtime
		String[] title = {"id","姓名","userage","usergender","mailbox","headportrait","创建时间","modifytime"};
		// 创建工作簿
		Workbook workbook = new HSSFWorkbook();
		// 创建工作表
		Sheet sheet =  workbook.createSheet();
		
		// 设置字体大小及样式
		Font font = workbook.createFont();
		CellStyle style = workbook.createCellStyle();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("宋体");
		
		// 设置字体居中
		style.setAlignment(HorizontalAlignment.CENTER);
	
		style.setFont(font);
		// 创建第一行，设置表头
		Row row  = sheet.createRow(0);
		// 设置表头字体居中
		row.setRowStyle(style);
		Cell cell = null;
		for(int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			// 设置表头字体样式
			cell.setCellStyle(style);
		}
		
		    // 创建第二行，单元格追加数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
		    int j = 1;
		   // 遍历所有数据
			for (Scholastic scholastic : list) {
				
			Row nextRow = sheet.createRow( j++ );
			nextRow.setRowStyle(style);
			// 第二行的单元格
			Cell nextCell = nextRow.createCell(0);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(scholastic.getId());
			// name
			nextCell = nextRow.createCell(1);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(scholastic.getUsername());
			
			nextCell = nextRow.createCell(2);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(scholastic.getUserage());
			
			nextCell = nextRow.createCell(3);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(scholastic.getUsergender());
			
			nextCell = nextRow.createCell(4);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(scholastic.getMailbox());
			
			nextCell = nextRow.createCell(5);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(scholastic.getHeadportrait());
			// 日期转换为指定格式  yyyy-MM:dd HH:mm:ss
			Date createTime = scholastic.getCreationtime();
			nextCell = nextRow.createCell(6);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(sdf.format(createTime));
			
			Date modifyTime = scholastic.getModifytime();
			nextCell = nextRow.createCell(7);
			nextCell.setCellStyle(style);
			nextCell.setCellValue(sdf.format(modifyTime));
		}
		
		// 导出Excel数据前，设置中文列宽自适应
		for(int k = 0; k < title.length; k++) {
		     sheet.autoSizeColumn(k);
		     sheet.setColumnWidth(k, sheet.getColumnWidth(k) * 16 / 10);
		}
		
		JsonResult  jsonResult = new JsonResult();
		// 创建一个文件,将内容写入到磁盘
		String pathFile = "E:\\";
		String pathname = pathFile + "alldata" + ".xls";
		File file = new File(pathname);
		
		try {
			FileOutputStream fos = FileUtils.openOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			jsonResult.setCode(101);
			jsonResult.setMsg("请关闭文件：" + pathname + " 后再进行数据导出！");
			return jsonResult;
		}
		
		jsonResult.setCode(200);
		jsonResult.setMsg("导出成功数据：" + list.size() + "条");
		return jsonResult;
	}
	
	
	
	
// 最后一个	
}
