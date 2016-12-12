package com.sjw.ShiroTest.Pojo;

import java.io.Serializable;

/**
 * Created by Watson on 12/12/2016.
 */
public class MsgWrapperPojo<T> implements Serializable {

    private static final long serialVersionUID = 4577280610296026013L;
    private String msg;
    private T t;

    public MsgWrapperPojo(String msg, T t) {
        this.msg = msg;
        this.t = t;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
