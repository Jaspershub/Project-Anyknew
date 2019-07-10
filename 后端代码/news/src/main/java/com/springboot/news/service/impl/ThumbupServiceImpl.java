package com.springboot.news.service.impl;

import com.springboot.news.dao.ThumbupDao;
import com.springboot.news.model.Thumbup;
import com.springboot.news.service.ThumbupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThumbupServiceImpl implements ThumbupService {
    @Autowired
    ThumbupDao thumbupDao;


    @Override
    public int deleteThumbup(int uid,String nid) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uid",uid);
        map.put("nid",nid);
        return thumbupDao.deleteThumbup(map);
    }

    @Override
    public Thumbup getThumbupByUidAndNid(int uid,String nid) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uid",uid);
        map.put("nid",nid);
        return  thumbupDao.selectThumbupByUidAndNid(map);
    }

    @Override
    public int saveThumbup(Thumbup thumbup) {
       if(getThumbupByUidAndNid(thumbup.getThuUid(),thumbup.getThuNid())==null){
           return thumbupDao.insertThumbup(thumbup);
       }else{
           return 0;
       }

    }
}
