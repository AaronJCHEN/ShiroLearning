package com.sjw.ShiroTest.Settings.Redis;


import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by Watson on 10/21/2016.
 */
public class SessionMsgListener {

    public void handleMessage(Serializable message){
       if(message!=null && "update session".equals(message)){

       }
    }
}
