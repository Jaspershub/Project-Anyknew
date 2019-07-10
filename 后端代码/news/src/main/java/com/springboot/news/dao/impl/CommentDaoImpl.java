package com.springboot.news.dao.impl;

import com.springboot.news.dao.CommentDao;
import com.springboot.news.mapper.CommentMapper;
import com.springboot.news.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentDaoImpl implements CommentDao {
    @Autowired
    CommentMapper commentMapper;

    public List<Comment> selectCommentByUid(Map<String,Object> map){
        return commentMapper.selectCommentByUid(map);
    }
    public List<Comment> selectCommentByNid(Map<String,Object> map){
        return commentMapper.selectCommentByNid(map);
    }
    public int insertComment(Map<String,Object> map){return commentMapper.insertComment(map);}
    public int deleteComment(int cid){
        return  commentMapper.deleteComment(cid);
    }
    public Comment selectCommentByCid(int cid){
        return commentMapper.selectToCommentByCid(cid);
    }
}
