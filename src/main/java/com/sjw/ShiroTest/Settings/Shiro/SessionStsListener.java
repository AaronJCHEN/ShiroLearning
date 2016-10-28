package com.sjw.ShiroTest.Settings.Shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Watson on 10/27/2016.
 */
public class SessionStsListener implements SessionListener {
    private Logger logger = LoggerFactory.getLogger(SessionStsListener.class);

    private String key;
    private SessionForRedisDao sessionForRedisDao;

    @Override
    public void onStart(Session session) {
        logger.info("onStart 触发 by {}", session.getHost());
    }

    @Override
    public void onStop(Session session) {
        logger.info("onStop 触发 ID 是{}", session.getId());
        sessionForRedisDao.doDelete(session);
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("onExpiration 触发 ID是{}", session.getId());
        sessionForRedisDao.doDelete(session);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSessionForRedisDao(SessionForRedisDao sessionForRedisDao) {
        this.sessionForRedisDao = sessionForRedisDao;
    }
}
