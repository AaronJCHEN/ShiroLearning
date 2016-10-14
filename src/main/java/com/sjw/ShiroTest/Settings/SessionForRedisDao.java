package com.sjw.ShiroTest.Settings;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
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
	private Boolean onlyEhCache = true;
	private int seconds = 0;


	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		// TODO Auto-generated method stub
		Session cachedSession = null;
		cachedSession = super.getCachedSession(sessionId);
		if(onlyEhCache)
			return cachedSession;
		else{
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
	}

	@Override
	protected void doUpdate(Session session) {
		if (!(session instanceof ValidatingSession) && ((ValidatingSession) session).isValid() ){
			if (!onlyEhCache){
				template.multi();
			}
		}
		else {
			logger.warn("Validating Session error");
		}

	}

	@Override
	protected void doDelete(Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session,sessionId);
		if (onlyEhCache) {
			return sessionId;
		}
		else{
			if(session != null && session.getId() != null) {
				session.setTimeout(seconds);
				valOps.set(session.getId(), session);
				valOps.getOperations().expire(session.getId(), session.getTimeout(), TimeUnit.MILLISECONDS);
				setOps.add(key, session.getId());
			}
		}
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

	public Boolean getOnlyEhCache() {
		return onlyEhCache;
	}

	public void setOnlyEhCache(Boolean onlyEhCache) {
		this.onlyEhCache = onlyEhCache;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
