package com.sjw.ShiroTest.Settings;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

public class SessionForRedisDao extends CachingSessionDAO {
	@Autowired
    private RedisTemplate<String, String> template;
	
	@Resource(name="redisTemplate")
    private ValueOperations<Serializable,Session> valOps;
	
	@Resource(name="redisTemplate")
	private SetOperations<String,Serializable> setOps;
	
	private final String key = "SessionList";
	private Logger logger = LoggerFactory.getLogger(SessionForRedisDao.class);


	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		// TODO Auto-generated method stub
		Session cachedSession = null;
		cachedSession = super.getCachedSession(sessionId);
		if(cachedSession ==null || cachedSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null){
			cachedSession = this.doReadSession(sessionId);
			if(cachedSession == null)
				throw new UnknownSessionException();
			else{
				((SessionForRedis)cachedSession).setNeedUpdate(true);
				super.update(cachedSession);
			}
		}
		return cachedSession;
	}

	@Override
	protected void doUpdate(Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doDelete(Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Serializable doCreate(Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
        try {
        	session = valOps.get(sessionId);
        	logger.info("shiro session id {} 被读取", sessionId);
        } catch (Exception e) {  
            e.printStackTrace();
        }
        return session;  
	}

}
