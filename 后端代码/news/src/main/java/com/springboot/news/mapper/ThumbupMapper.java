package com.springboot.news.mapper;

import com.springboot.news.model.Thumbup;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ThumbupMapper {

    int deleteThumbup(Map<String,Object> map);
    Thumbup selectThumbupByUidAndNid(Map<String,Object> map);
    int insertThumbup(Thumbup thumbup);
}