package com.springboot.news.model;

public class Thumbup {
    private Integer thuId;

    private String thuNid;

    private Integer thuUid;

    public Thumbup(Integer thuId, String thuNid, Integer thuUid) {
        this.thuId = thuId;
        this.thuNid = thuNid;
        this.thuUid = thuUid;
    }

    public Thumbup() {
        super();
    }

    public Integer getThuId() {
        return thuId;
    }

    public void setThuId(Integer thuId) {
        this.thuId = thuId;
    }

    public String getThuNid() {
        return thuNid;
    }

    public void setThuNid(String thuNid) {
        this.thuNid = thuNid == null ? null : thuNid.trim();
    }

    public Integer getThuUid() {
        return thuUid;
    }

    public void setThuUid(Integer thuUid) {
        this.thuUid = thuUid;
    }
}