package com.springboot.news.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.springboot.news.util.FastJSONUtil;

import java.io.Serializable;
import java.util.Map;

public class JSONResult implements Serializable {

    @JSONField(ordinal = 1)
    private int state;
    @JSONField(ordinal = 2)
    private String msg;
    @JSONField(ordinal = 3)
    private Object data;

    public JSONResult(int state,String msg,Object data){
        this.state=state;
        this.msg=msg;
        this.data=data;
    }
    public JSONResult(int state,String msg){
        this.state=state;
        this.msg=msg;
    }

    public static String build(int state,String msg,Object data){
        return FastJSONUtil.convertObjectToJSON(new JSONResult(state,msg,data));
    }
    public static String build(int state,String msg){
        return FastJSONUtil.convertObjectToJSON(new JSONResult(state,msg));
    }

    public static String ok(Object data){
        int state=1000;
        String msg="success";
        return FastJSONUtil.convertObjectToJSON(new JSONResult(state,msg,data));
    }
    public static String exception(int state,String msg){
        return FastJSONUtil.convertObjectToJSON(new JSONResult(state,msg));
    }


    public int getState() {
        return state;
    }

    public  void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



}
