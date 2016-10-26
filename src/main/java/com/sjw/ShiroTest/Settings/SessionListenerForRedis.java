package com.sjw.ShiroTest.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 10/21/2016.
 */
public class SessionListenerForRedis {
    @Autowired
    JdkSerializationRedisSerializer jdkSerializationRedisSerializer;

    public void handleMessage(Serializable message){
       /* if(message == null){
            System.out.println("null");
        } else if(message.getClass().isArray()){
            System.out.println(Arrays.toString((Object[])message));
        } else if(message instanceof List<?>) {
            System.out.println(message);
        } else if(message instanceof Map<? , ?>) {
            System.out.println(message);
        } else {
            System.out.println("Message is : "+message);
        }*/
       if(message!=null && "update session".equals(message)){
           
       }
    }
}
