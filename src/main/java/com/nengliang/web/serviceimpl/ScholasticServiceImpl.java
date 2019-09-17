package com.nengliang.web.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nengliang.web.dao.ScholasticDao;
import com.nengliang.web.entity.Scholastic;
import com.nengliang.web.service.ScholasticService;

@Service
public class ScholasticServiceImpl implements ScholasticService{

	@Autowired
	private ScholasticDao  schDao;

	public List<Scholastic> selectParam() {
		// TODO Auto-generated method stub
		return schDao.selectParam();
	}

	public List<Scholastic> selectAll(int pages, Integer limit) {
		// TODO Auto-generated method stub
		return schDao.selectAll(pages,limit);
	}
	
	
	
	
	
}
