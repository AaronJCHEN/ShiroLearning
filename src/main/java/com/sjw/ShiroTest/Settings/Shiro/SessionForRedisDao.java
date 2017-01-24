package com.sjw.ShiroTest.Settings.Shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Transactional
public class SessionForRedisDao extends CachingSessionDAO {

	@Resource(name = "redisTemplate")
	private ValueOperations<String,Session> valOps;

	@Resource(name = "redisTemplate")
	private ValueOperations<String,Serializable> valOpsForStr;

	@Resource(name = "txRedisTemplate")
	private RedisTemplate<String,Serializable> txTemplate;

	@Resource(name = "txRedisTemplate")
	private ValueOperations<String,Session> txValOps;

	@Resource(name = "txRedisTemplate")
	private SetOperations<String,String> txSetOps;
	
	private String key;
	private Logger logger = LoggerFactory.getLogger(SessionForRedisDao.class);
	private Boolean onlyEhCache = true;
	private int seconds = 0;
	private boolean isCreated = false;
	private boolean isDeleted = false;


	@Override
	public Session readSession(Serializable sessionId) throws UnknownSessionException {
		Session cachedSession = null;
		cachedSession = super.getCachedSession(sessionId);
		if(onlyEhCache)
			return cachedSession;
		else{
			if(cachedSession ==null || cachedSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null){
				cachedSession = this.doReadSession(sessionId);
				if(cachedSession == null) {
					if (isCreated)
						throw new UnknownSessionException("Can't find the session. Please reopen the browser");
				}
				else{
					//TODO to see if this part is necessary
					logger.debug("没有取得授权信息");
					((SessionForRedis)cachedSession).setNeedUpdate(true);
					super.update(cachedSession);
					((SessionForRedis)cachedSession).setNeedUpdate(false);  //TODO need to confirm if it's necessary
				}
			}
			else if(cachedSession !=null)
				logger.debug("从Ehcache读取session,IP为{}",cachedSession.getHost());
			return cachedSession;
		}
	}

	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession || ((ValidatingSession) session).isValid() ){
			if (!onlyEhCache){
				if (session instanceof SessionForRedis){
					SessionForRedis ss = (SessionForRedis) session;
					logger.info("The session which ip is {} is being updated",ss.getHost());
					if(ss.isNeedUpdate()){
						ss.setNeedUpdate(false);
						ss.setLastAccessTime(new Date());
						txValOps.set(session.getHost(), ss);
						txValOps.getOperations().expire(session.getHost(), ss.getTimeout(), TimeUnit.MILLISECONDS);
					}
				}
				else if (session instanceof Serializable){
					txValOps.set(session.getHost(), session);
					txValOps.getOperations().expire(session.getHost(), session.getTimeout(), TimeUnit.MILLISECONDS);
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
	protected void doDelete(Session session) {
		logger.info("Begin to delete a session");
		if (txValOps.get(session.getHost()) != null)
			txTemplate.delete(session.getHost());
		if (txSetOps.members(key).contains(session.getHost()))
			txSetOps.remove(key,session.getHost());
		if (txValOps.get("SessionId:"+session.getId()) != null)
			txTemplate.delete("SessionId:"+session.getId());
		this.isDeleted = true;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session,sessionId);
		if (onlyEhCache) {
			isCreated = true;
			this.isDeleted = false;
			return sessionId;
		}
		else{
			if(session != null && session.getHost() != null) {
				logger.info("Begin to create a new session");
				session.setTimeout(seconds);
				txValOps.getOperations().expire(session.getHost(), session.getTimeout(), TimeUnit.MILLISECONDS);
				txValOps.set(session.getHost(), session);
				txSetOps.add(key, session.getHost());
				valOpsForStr.set("SessionId:"+session.getId(),session.getHost());
				isCreated = true;
				this.isDeleted = false;
			}
		}
		logger.info("doCreate 完成,此时session id为{}",session.getId());
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
        try {
        	if (!this.isDeleted) {
				String ip = (String) valOpsForStr.get("SessionId:" + sessionId);
				session = valOps.get(ip);
				logger.info("shiro session id {} 被读取", session.getHost());
			}
			else
				logger.info("shiro session id {} 已被删除");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return session;
	}

	@Override
	protected void cache(Session session, Serializable sessionId) {
		super.cache(session, sessionId);
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
