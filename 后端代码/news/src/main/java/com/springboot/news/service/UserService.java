package com.springboot.news.service;

import com.springboot.news.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    List<HashMap<String,Object>> getAllUser();
    String getUidByOpenId(String openid);
    int saveUser(User user);
    int modifyUser(User user);
    User getUserByUid(int uid);
}
