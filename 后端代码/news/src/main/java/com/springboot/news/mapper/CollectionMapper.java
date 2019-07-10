package com.springboot.news.mapper;

import com.springboot.news.model.Collect;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface CollectionMapper {

    Collect selectCollectionByUidAndNid(Map<String,Object> map);
    List<Collect> selectAllCollectionByUid(Map<String,Object> map);
    int insertCollection(Collect collect);
   int deleteCollection(Map<String,Object> map);

}