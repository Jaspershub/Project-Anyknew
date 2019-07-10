package com.springboot.news.service;

import com.springboot.news.model.Collect;


import java.util.List;
import java.util.Map;

public interface CollectionService {

    List<Collect> getAllCollectionByUid(String uid,int page,int num);
    int saveCollection(Collect collect);
    int deleteCollection(int uid,String nid);
    Collect selectCollectionByUidAndNid(int uid,String nid);

}
