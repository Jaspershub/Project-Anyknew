package com.springboot.news.mapper;

import com.springboot.news.model.Reading;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ReadingMapper {

    String selectReadingByNid(String nid);
    int insertArticleToReading(Reading reading);
    int updateArticleReading(Map<String,String> map);
}