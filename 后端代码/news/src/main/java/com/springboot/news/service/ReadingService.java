package com.springboot.news.service;

import com.springboot.news.model.Reading;

public interface ReadingService {

    String getReadingByNid(String nid);
    int saveArticleToReading(Reading reading);
    int modifyArticleReading(String nid,String ntitle,String channelid);
}
