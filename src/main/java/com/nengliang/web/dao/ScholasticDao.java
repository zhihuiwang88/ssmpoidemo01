package com.nengliang.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nengliang.web.entity.Scholastic;

public interface ScholasticDao {

	List<Scholastic> selectParam();

	List<Scholastic> selectAll(@Param("pages") int pages, @Param("limits") Integer limit);

}
