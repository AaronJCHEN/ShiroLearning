package com.sjw.ShiroTest.Shiro;

import com.sjw.ShiroTest.Msg.RedisMsgSender;
import com.sjw.ShiroTest.Msg.SocketHandler;
import org.apache.shiro.session.InvalidSessionException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

	@Autowired
	RedisMsgSender redisMsgSender;
	
	private String key;
	private Logger logger = LoggerFactory.getLogger(SessionForRedisDao.class);
	private Boolean onlyEhCache = true;
	private int seconds = 0;
	private boolean isCreated = false;
	private boolean isDeleted = false;
	private static String staticFile = ".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd";


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
			/*else if(cachedSession !=null)
				logger.info("Session is read from Ehcache, which IP is {}",cachedSession.getHost());*/
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
						redisMsgSender.sender("session-update","update session:"+session.getHost());
					}
				}
				else if (session instanceof Serializable){
					txValOps.set(session.getHost(), session);
					txValOps.getOperations().expire(session.getHost(), session.getTimeout(), TimeUnit.MILLISECONDS);
					logger.info("A Session that doesn't fit for redis is recorded");
					redisMsgSender.sender("session-update","update session:"+session.getHost());
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
		if(SocketHandler.sessionList.containsKey(session.getId())){
			WebSocketSession webSocketSession = SocketHandler.sessionList.get(session.getId());
			TextMessage textMessage = new TextMessage("session timeout");
			try {
				logger.info("Notify user for session delete phrase");
				webSocketSession.sendMessage(textMessage);
				SocketHandler.sessionList.remove(session.getId());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		this.isDeleted = true;
	}

	@Override
	protected synchronized Serializable doCreate(Session session) {
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
				txValOps.set(session.getHost(), session);
				txValOps.getOperations().expire(session.getHost(), session.getTimeout(), TimeUnit.MILLISECONDS);
				txSetOps.add(key, session.getHost());
				txSetOps.getOperations().expire(key,session.getTimeout(), TimeUnit.MILLISECONDS);
				valOpsForStr.set("SessionId:"+session.getId(),session.getHost());
				valOpsForStr.getOperations().expire("SessionId:"+session.getId(),session.getTimeout(), TimeUnit.MILLISECONDS);
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
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String uri = request.getServletPath();
		/*if (request != null){
			String uri = request.getServletPath();
			// Judge if it's the static file


			session = (Session)request.getAttribute("session_"+sessionId);
		}
		if (session != null){
			return session;
		}*/
        try {
        	if (!this.isDeleted) {
				String ip = (String) valOpsForStr.get("SessionId:" + sessionId);
				session = valOps.get(ip);
				logger.info("session is read by Redis, which ID is {}", session.getHost());
			}
			else {
				logger.info("shiro session id {} 已被删除");
				throw new InvalidSessionException();
			}
		} catch (InvalidSessionException e) {
        	this.isDeleted = true;
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e){
			this.isDeleted = true;
			e.printStackTrace();
		}
        return session;
	}

	public void doUpdateToEhcache(String host){

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
