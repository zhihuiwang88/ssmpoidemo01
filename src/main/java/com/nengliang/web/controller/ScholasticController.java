package com.nengliang.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nengliang.web.entity.Scholastic;
import com.nengliang.web.service.ScholasticService;
import com.nengliang.web.util.JsonResult;

@Controller
@RequestMapping("/scholastic")
public class ScholasticController {

	private static final Logger  Logger = LoggerFactory.getLogger(ScholasticController.class);
	
	@Autowired
	private ScholasticService  schService;
	
	
	/**
	 * 查询所有数据并分页展示
	 * localhost:8081/ssmupload/scholastic/listAll
	 * @return
	 */
	@RequestMapping("/listAll")
	public String   selectList() {
		return	"list";	
	}
	
	@RequestMapping("/selectAll")
	@ResponseBody
	public  JsonResult  selectAllPerson(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit) {
		JsonResult jsonResult = new JsonResult();
		int pages = (page-1)*limit;
		
		List<Scholastic>  schcList =  schService.selectParam();
		List<Scholastic>  scholasticList =  schService.selectAll(pages,limit);
		jsonResult.setCode(0);
		jsonResult.setCount(schcList.size());
		jsonResult.setData(scholasticList);
		return	jsonResult;	
	}
	
}
