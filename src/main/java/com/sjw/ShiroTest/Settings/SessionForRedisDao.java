package com.sjw.ShiroTest.Settings;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SessionForRedisDao extends CachingSessionDAO {
	@Resource(name="redisTemplate")
    private RedisTemplate<String, String> template;
	
	@Resource(name="redisTemplate")
    private ValueOperations<Serializable,Session> valOps;
	
	@Resource(name="redisTemplate")
	private SetOperations<String,Serializable> setOps;

	@Resource(name="txRedisTemplate")
	private RedisTemplate<String,String > txTemplate;

	@Resource(name="txRedisTemplate")
	private ValueOperations<Serializable,Session> txValOps;

	@Resource(name="txRedisTemplate")
	private SetOperations<String,Serializable> txSetOps;
	
	private final String key = "SessionList";
	private Logger logger = LoggerFactory.getLogger(SessionForRedisDao.class);
	private Boolean onlyEhCache = true;
	private int seconds = 0;


	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
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
	@Transactional
	protected void doUpdate(Session session) {
		if (!(session instanceof ValidatingSession) && ((ValidatingSession) session).isValid() ){
			if (!onlyEhCache){
				if (session instanceof SessionForRedis){
					SessionForRedis ss = (SessionForRedis) session;
					if(ss.isNeedUpdate()){
						ss.setNeedUpdate(false);
						ss.setLastAccessTime(new Date());
						txValOps.set(session.getId(), ss);
						txValOps.getOperations().expire(ss.getId(), ss.getTimeout(), TimeUnit.MILLISECONDS);
					}
				}
				else if (session instanceof Serializable){
					txValOps.set(session.getId(), session);
					txValOps.getOperations().expire(session.getId(), session.getTimeout(), TimeUnit.MILLISECONDS);
					logger.info("A Session that doesn't fit for redis is recorded");
				}
				else{
					logger.warn("The session can't be serialized");
				}
			}
			else {
				logger.info("Update Info: Using Ehcache to get session");
			}
		}
		else {
			logger.warn("Validating Session error");
		}

	}

	@Override
	@Transactional
	protected void doDelete(Session session) {
		logger.info("Begin to delete a session");
		txTemplate.delete((String) session.getId());
		txSetOps.remove(key,session.getId());
	}

	@Override
	@Transactional
	protected Serializable doCreate(Session session) {
		logger.info("Begin to create a new session");
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session,sessionId);
		if (onlyEhCache) {
			return sessionId;
		}
		else{
			if(session != null && session.getId() != null) {
				session.setTimeout(seconds);
				txValOps.set(session.getId(), session);
				txValOps.getOperations().expire(session.getId(), session.getTimeout(), TimeUnit.MILLISECONDS);
				txSetOps.add(key, session.getId());
			}
		}
		return sessionId;
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
