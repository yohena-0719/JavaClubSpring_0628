package com.example.app.mapper;

import org.apache.ibatis.annotations.Param;

public interface NewsTargetMapper {
	void insert(@Param("newsId") Integer newsId, @Param("typeId") Integer typeId) throws Exception;

}
