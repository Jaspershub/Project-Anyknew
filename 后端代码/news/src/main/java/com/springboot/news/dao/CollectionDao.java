package com.springboot.news.dao;

import com.springboot.news.model.Collect;


import java.util.List;
import java.util.Map;

public interface CollectionDao {

    Collect selectCollectionByUidAndNid(Map<String,Object> map);
    List<Collect> selectAllCollectionByUid(Map<String,Object> map);
    int insertCollection(Collect collect);
    int deleteCollection(Map<String,Object> map);

}
