package com.springboot.news.dao;

import com.springboot.news.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    List<HashMap<String,Object>> selectAllUser();
    String selectUidByOpenId(String openid);
    int insertUser(User user);
    int updateUser(User user);
    User selectUserByUid(int uid);
}
