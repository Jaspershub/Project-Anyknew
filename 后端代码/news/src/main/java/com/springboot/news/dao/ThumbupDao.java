package com.springboot.news.dao;

import com.springboot.news.model.Thumbup;

import java.util.Map;

public interface ThumbupDao {

    int deleteThumbup(Map<String,Object> map);
    Thumbup selectThumbupByUidAndNid(Map<String,Object> map);
    int insertThumbup(Thumbup thumbup);
}
