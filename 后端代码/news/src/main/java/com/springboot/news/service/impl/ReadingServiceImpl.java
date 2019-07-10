package com.springboot.news.service.impl;

import com.springboot.news.dao.ReadingDao;
import com.springboot.news.model.Reading;
import com.springboot.news.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class ReadingServiceImpl implements ReadingService {

    @Autowired
    ReadingDao readingDao;

    /**
     * 通过nid获得文章阅读量
     * @param nid
     * @return
     */
    public String getReadingByNid(String nid){


            return readingDao.selectReadingByNid(nid);

    }

    /**
     * 保存一条新文章记录
     * @param reading
     * @return
     */
    public int  saveArticleToReading(Reading reading){
return readingDao.insertArticleToReading(reading);
    }

    /**
     * 修改文章阅读量+1
     * @param nid,ntitle,channelid
     * @return
     */
    public int modifyArticleReading(String nid,String ntitle,String channelid){
        Map<String,String> map=new HashMap<String,String>();
        map.put("ntitle",ntitle);
        map.put("nid",nid);
        map.put("channelid",channelid);
        return readingDao.updateArticleReading(map);
    }
}
