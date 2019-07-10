package com.springboot.news.mapper;


import com.springboot.news.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {

Comment selectToCommentByCid(String cid);
List<Comment> selectCommentByUid(Map<String,Object> map);
    List<Comment> selectCommentByNid(Map<String,Object> map);
    int insertComment(Map<String,Object> map);
    int deleteComment(int cid);
    Comment selectToCommentByCid(int cid);
}