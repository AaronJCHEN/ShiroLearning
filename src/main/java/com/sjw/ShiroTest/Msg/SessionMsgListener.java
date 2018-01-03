package com.sjw.ShiroTest.Msg;


import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Watson on 10/21/2016.
 */
@Component
public class SessionMsgListener {
   /* @Autowired
    SessionForRedisDao sessionForRedisDao;*/

    public void handleMessage(Serializable message){
       if (message!=null){
           if (message instanceof String && ((String) message).contains("update session")) {
               String host = ((String) message).substring(((String) message).indexOf(":"));
               //sessionForRedisDao.doUpdateToEhcache(host);
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
