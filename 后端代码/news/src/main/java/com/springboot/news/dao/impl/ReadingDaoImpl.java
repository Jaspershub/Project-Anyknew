package com.springboot.news.dao.impl;

import com.springboot.news.dao.ReadingDao;
import com.springboot.news.mapper.ReadingMapper;
import com.springboot.news.model.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ReadingDaoImpl  implements ReadingDao {
    @Autowired
    ReadingMapper readingMapper;


    public String selectReadingByNid(String nid){

        return readingMapper.selectReadingByNid(nid);
    }
    public int insertArticleToReading(Reading reading){
        return readingMapper.insertArticleToReading(reading);
    }

    public int updateArticleReading(Map<String,String> map){
        return readingMapper.updateArticleReading(map);
    }


}
