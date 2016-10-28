package com.sjw.ShiroTest.Settings.Shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Watson on 10/27/2016.
 */
public class SessionStsListener implements SessionListener {
    private Logger logger = LoggerFactory.getLogger(SessionStsListener.class);
    private String key;

    @Resource(name="redisTemplate")
    private SetOperations<String,String> setOps;

    @Override
    public void onStart(Session session) {
        logger.info("onStart 触发 by {}", session.getHost());
    }

    @Override
    public void onStop(Session session) {
        logger.info("onStop 触发", session.getId());
        logger.info("Session the ID 是 {}",session.getHost());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("onExpiration 触发", session.getId());
        logger.info("Session the ID 是 {}",session.getHost());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
