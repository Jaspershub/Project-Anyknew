package com.springboot.news.service.impl;

import com.springboot.news.dao.UserDao;
import com.springboot.news.model.User;
import com.springboot.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    public List<HashMap<String,Object>> getAllUser(){
        return userDao.selectAllUser();
    }

    public String getUidByOpenId(String openid){
        return userDao.selectUidByOpenId(openid);
    }
   public int saveUser(User user){
        return userDao.insertUser(user);
   }
   public int modifyUser(User user){
        return userDao.updateUser(user);
   }
    public User getUserByUid(int uid){
        return userDao.selectUserByUid(uid);
    }

}
