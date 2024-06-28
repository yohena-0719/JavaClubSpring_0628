package com.example.app.mapper;

import java.util.List;

import com.example.app.domain.News;

public interface NewsMapper {
	List<News> selectAll() throws Exception;

	News selectById(Integer id) throws Exception;

	void insert(News news) throws Exception;

}
