package com.sjw.ShiroTest.Pojo;

import org.springframework.http.HttpStatus;

public class ResponsePojo {
    private int status;
    private String msg;
    private String detail;
    private Object info;

    public ResponsePojo(){
        this.status = HttpStatus.OK.value();
    }

    public ResponsePojo(int status, String msg, String detail) {
        this.status = status;
        this.msg = msg;
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
