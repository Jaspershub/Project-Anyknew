package com.springboot.news.service;

import com.springboot.news.model.Thumbup;

import java.util.Map;

public interface ThumbupService {

    int deleteThumbup(int uid,String nid);
    Thumbup getThumbupByUidAndNid(int ud,String nid);
    int saveThumbup(Thumbup thumbup);

}
