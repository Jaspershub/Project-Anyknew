package com.springboot.news.dao.impl;

import com.springboot.news.dao.ThumbupDao;
import com.springboot.news.mapper.ThumbupMapper;
import com.springboot.news.model.Thumbup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ThumbupDaoImpl implements ThumbupDao {
    @Autowired
    ThumbupMapper thumbupMapper;

    @Override
    public int deleteThumbup(Map<String, Object> map) {
        return thumbupMapper.deleteThumbup(map);
    }

    @Override
    public Thumbup selectThumbupByUidAndNid(Map<String, Object> map) {
        return thumbupMapper.selectThumbupByUidAndNid(map);
    }

    @Override
    public int insertThumbup(Thumbup thumbup) {


        return thumbupMapper.insertThumbup(thumbup);
    }
}
