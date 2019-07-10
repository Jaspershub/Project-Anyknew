package com.springboot.news.dao;


import com.springboot.news.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentDao {

    List<Comment> selectCommentByUid(Map<String,Object> map);
    List<Comment> selectCommentByNid(Map<String,Object> map);
    int insertComment(Map<String,Object> map);
    int deleteComment(int cid);
    Comment selectCommentByCid(int cid);
}
