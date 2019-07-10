package com.springboot.news.model;

public class User {
    private Integer uId;

    private String wname;

    private Integer sex;

    private Integer age;

    private String country;

    private String province;

    private String city;

    private String headimg;

    private String openid;

    public User(Integer uId, String wname, Integer sex, Integer age, String country, String province, String city, String headimg, String openid) {
        this.uId = uId;
        this.wname = wname;
        this.sex = sex;
        this.age = age;
        this.country = country;
        this.province = province;
        this.city = city;
        this.headimg = headimg;
        this.openid = openid;
    }

    public User() {
        super();
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname == null ? null : wname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg == null ? null : headimg.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }
}