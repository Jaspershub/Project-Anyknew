package com.springboot.news.dao.impl;

import com.springboot.news.dao.CollectionDao;
import com.springboot.news.mapper.CollectionMapper;
import com.springboot.news.model.Collect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CollectionDaoImpl  implements CollectionDao {
    @Autowired
    CollectionMapper collectionMapper;


    @Override
    public Collect selectCollectionByUidAndNid(Map<String, Object> map) {
        return collectionMapper.selectCollectionByUidAndNid(map);
    }

    @Override
    public List<Collect> selectAllCollectionByUid(Map<String, Object> map) {
        return collectionMapper.selectAllCollectionByUid(map);
    }

    @Override
    public int deleteCollection(Map<String,Object> map) {
        return collectionMapper.deleteCollection(map);
    }

    @Override
    public  int insertCollection(Collect collect){
        return collectionMapper.insertCollection(collect);
    }
}
