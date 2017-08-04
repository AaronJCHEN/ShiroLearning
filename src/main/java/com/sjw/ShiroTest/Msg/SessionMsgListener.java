package com.sjw.ShiroTest.Msg;


import com.sjw.ShiroTest.Settings.Shiro.SessionForRedisDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * Created by Watson on 10/21/2016.
 */
@Component
public class SessionMsgListener {
    @Autowired
    SessionForRedisDao redisDao;

    public void handleMessage(Serializable message){
       if (message!=null){
           if (message instanceof String && ((String) message).contains("update session")) {
               String host = ((String) message).substring(((String) message).indexOf(":"));
               redisDao.doUpdateToEhcache(host);
           }
           else
               throw new RuntimeException("Update session msg format is wrong");
       }
       else
           throw new RuntimeException("Update session msg is null");
    }

    public void handleDelMessage(Serializable message){

    }
}
