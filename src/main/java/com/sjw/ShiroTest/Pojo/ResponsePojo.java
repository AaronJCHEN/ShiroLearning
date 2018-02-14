package com.sjw.ShiroTest.Pojo;

import org.springframework.http.HttpStatus;

public class ResponsePojo {

    private int code;

    private String msg;

    private Object objData;

    public ResponsePojo(int value, String failture){

    }

    public ResponsePojo(int code, String msg, Object objData){
        this.code = code;
        this.msg = msg;
        this.objData = objData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObjData() {
        return objData;
    }

    public void setObjData(Object objData) {
        this.objData = objData;
    }
}
