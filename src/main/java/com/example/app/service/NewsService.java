package com.example.app.service;

import java.util.List;

import com.example.app.domain.News;
import com.example.app.domain.NewsForm;

public interface NewsService {
	List<News> getNewsList() throws Exception;

	News getNewsById(Integer id) throws Exception;

	void addNews(NewsForm formData) throws Exception;

}
