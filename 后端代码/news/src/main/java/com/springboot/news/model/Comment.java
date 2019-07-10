package com.springboot.news.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Comment  {
    private Integer cId;

    private String cNid;

    private String content;

    public int getcDelete() {
        return cDelete;
    }

    public void setcDelete(int cDelete) {
        this.cDelete = cDelete;
    }

    private Date cDate;
    private User user;
    private  Comment toComment;
private  int cDelete;

    public Comment getToComment() {
        return toComment;
    }

    public void setToComment(Comment toComment) {
        this.toComment = toComment;
    }

    public User getUser() {
        return user;
    }

    public Comment(Integer cId, String cNid, String content, Date cDate, User user, Comment toComment, int cDelete) {
        this.cId = cId;
        this.cNid = cNid;
        this.content = content;
        this.cDate = cDate;
        this.user = user;
        this.toComment = toComment;
        this.cDelete = cDelete;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Comment() {
        super();
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public String getcNid() {
        return cNid;
    }

    public void setcNid(String cNid) {
        this.cNid = cNid == null ? null : cNid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }



    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }
}