package com.springboot.news.model;

import java.util.Date;

public class Collect {
    private Integer colId;

    private String colNid;

    private Integer colUid;

    private String colNtitle;

    private Date colDate;

    public Collect(Integer colId, String colNid, Integer colUid, String colNtitle, Date colDate) {
        this.colId = colId;
        this.colNid = colNid;
        this.colUid = colUid;
        this.colNtitle = colNtitle;
        this.colDate = colDate;
    }

    public Collect() {
        super();
    }

    public Integer getColId() {
        return colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

    public String getColNid() {
        return colNid;
    }

    public void setColNid(String colNid) {
        this.colNid = colNid == null ? null : colNid.trim();
    }

    public Integer getColUid() {
        return colUid;
    }

    public void setColUid(Integer colUid) {
        this.colUid = colUid;
    }

    public String getColNtitle() {
        return colNtitle;
    }

    public void setColNtitle(String colNtitle) {
        this.colNtitle = colNtitle == null ? null : colNtitle.trim();
    }

    public Date getColDate() {
        return colDate;
    }

    public void setColDate(Date colDate) {
        this.colDate = colDate;
    }
}