package com.springboot.news.service.impl;

import com.springboot.news.dao.CommentDao;
import com.springboot.news.model.Comment;
import com.springboot.news.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    public List<Comment> getCommentByUid(String uid,int page,int num) {
        Map<String,Object> map=new HashMap<String,Object>();
map.put("uid",uid);
            map.put("begin",(page-1)*num);
        map.put("num",num);
        return commentDao.selectCommentByUid(map);
    }
    public List<Comment> getCommentByNid(String nid,int page,int num) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("nid",nid);
        map.put("begin",(page-1)*num);
        map.put("num",num);
        return commentDao.selectCommentByNid(map);
    }
    public int saveComment(String nid,int fromUid,String content,int toCid){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("nid",nid);
        map.put("fromUid",fromUid);
        map.put("content",content);
        map.put("toCid",toCid);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("cDate",  simpleDateFormat.format(new Date()));
        return  commentDao.insertComment(map);
    }
    public int deleteComment(int cid){
        return commentDao.deleteComment(cid);
    }
    public Comment getCommentByCid(int cid){
        return commentDao.selectCommentByCid(cid);
    }
}
