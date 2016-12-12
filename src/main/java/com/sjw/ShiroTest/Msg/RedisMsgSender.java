package com.sjw.ShiroTest.Msg;

import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Watson on 10/21/2016.
 */

@Component
public class RedisMsgSender {
    @Resource(name = "redisTemplate")
    RedisTemplate<String,Object> template;

    //private Logger logger = LoggerFactory.getLogger(RedisMsgSender.class);

    public boolean sender(String channel, Serializable msg){
        Boolean isSent = false;
        try {
            template.convertAndSend(channel,msg);
            isSent = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isSent;
    }
}
