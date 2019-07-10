package com.springboot.news.mapper;


import com.springboot.news.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {
    List<HashMap<String,Object>> selectAllUser();
    User selectUserByUid(int uid);
    String selectUidByOpenId(String openid);
    int insertUser(User user);
    int updateUser(User user);
}