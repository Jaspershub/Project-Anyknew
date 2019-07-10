package com.springboot.news.service.impl;

import com.springboot.news.dao.CollectionDao;
import com.springboot.news.model.Collect;
import com.springboot.news.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionDao collectionDao;
   public List<Collect> getAllCollectionByUid(String uid,int page,int num){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uid",uid);
        map.put("begin",(page-1)*num);
        map.put("num",num);
        return collectionDao.selectAllCollectionByUid(map);
    }

    @Override
    public int saveCollection(Collect collect) {

       if(selectCollectionByUidAndNid(collect.getColUid(),collect.getColNid())==null){
           return collectionDao.insertCollection(collect);
       }else{
           return 0;
       }


    }

    public int deleteCollection(int uid,String nid){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uid",uid);
        map.put("nid",nid);

return collectionDao.deleteCollection(map);
    }

    @Override
    public Collect selectCollectionByUidAndNid(int uid, String nid) {
       Map<String,Object> map=new HashMap<String,Object>();
       map.put("uid",uid);
       map.put("nid",nid);
        return collectionDao.selectCollectionByUidAndNid(map);
    }


}
