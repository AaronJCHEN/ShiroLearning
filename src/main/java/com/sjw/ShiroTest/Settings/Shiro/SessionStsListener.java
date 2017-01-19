package com.sjw.ShiroTest.Settings.Shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
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
    
    private CachingSessionDAO sessionDao;

    @Override
    public void onStart(Session session) {
        logger.info("onStart 触发 by {}", session.getHost());
    }

    @Override
    public void onStop(Session session) {
        logger.info("onStop 触发 ID 是{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("onExpiration 触发 ID是{}", session.getId());
        //sessionDao = new SessionForRedisDao();
        //sessionDao.delete(session);
    }

   /* public void setSessionDao(SessionForRedisDao sessionDao) {
        this.sessionDao = sessionDao;
    }*/
}
