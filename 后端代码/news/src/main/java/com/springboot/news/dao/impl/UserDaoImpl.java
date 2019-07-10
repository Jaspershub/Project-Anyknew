package com.springboot.news.dao.impl;

import com.springboot.news.dao.UserDao;
import com.springboot.news.mapper.UserMapper;
import com.springboot.news.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserMapper userMapper;
    public List<HashMap<String,Object>> selectAllUser(){
        return userMapper.selectAllUser();
    }

    public String selectUidByOpenId(String openid){
        return userMapper.selectUidByOpenId(openid);
    }

   public int insertUser(User user){
        return userMapper.insertUser(user);
   }

    public int updateUser(User user){
        return  userMapper.updateUser(user);
    }
    public User selectUserByUid(int uid){
        return userMapper.selectUserByUid(uid);
    }

}
