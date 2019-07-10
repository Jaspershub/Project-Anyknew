package com.springboot.news.dao;

import com.springboot.news.model.Reading;

import java.util.Map;

public interface ReadingDao {
    String selectReadingByNid(String nid);
    int insertArticleToReading(Reading reading);
    int updateArticleReading(Map<String,String> map);
}
