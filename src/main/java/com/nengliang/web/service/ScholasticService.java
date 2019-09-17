package com.nengliang.web.service;

import java.util.List;

import com.nengliang.web.entity.Scholastic;

public interface ScholasticService {

	List<Scholastic> selectParam();

	List<Scholastic> selectAll(int pages, Integer limit);

}
