package com.springboot.news.service;

import com.springboot.news.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> getCommentByUid(String uid,int page,int num);
    List<Comment> getCommentByNid(String uid,int page,int num);
    int saveComment(String nid,int fromUid,String content,int toCid);
    int deleteComment(int cid);
    Comment getCommentByCid(int cid);
}
