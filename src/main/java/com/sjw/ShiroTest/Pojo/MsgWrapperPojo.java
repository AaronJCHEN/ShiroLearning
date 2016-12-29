package com.sjw.ShiroTest.Pojo;

import java.io.Serializable;

/**
 * Created by Watson on 12/12/2016.
 */
public class MsgWrapperPojo<T> implements Serializable {

    private static final long serialVersionUID = 4577280610296026013L;
    private String msg;
    private T obj;
    private String username;

    public MsgWrapperPojo(){}

    public MsgWrapperPojo(String msg, String username, T obj) {
        this.msg = msg;
        this.username = username;
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
