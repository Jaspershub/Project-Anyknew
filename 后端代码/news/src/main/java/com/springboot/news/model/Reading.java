package com.springboot.news.model;

public class Reading {
    private Integer rId;

    private String rNid;

    private String rNtitle;

    private String charnnelid;

    private Integer num;

    public Reading(Integer rId, String rNid, String rNtitle, String charnnelid, Integer num) {
        this.rId = rId;
        this.rNid = rNid;
        this.rNtitle = rNtitle;
        this.charnnelid = charnnelid;
        this.num = num;
    }

    public Reading() {
        super();
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getrNid() {
        return rNid;
    }

    public void setrNid(String rNid) {
        this.rNid = rNid == null ? null : rNid.trim();
    }

    public String getrNtitle() {
        return rNtitle;
    }

    public void setrNtitle(String rNtitle) {
        this.rNtitle = rNtitle == null ? null : rNtitle.trim();
    }

    public String getCharnnelid() {
        return charnnelid;
    }

    public void setCharnnelid(String charnnelid) {
        this.charnnelid = charnnelid == null ? null : charnnelid.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}