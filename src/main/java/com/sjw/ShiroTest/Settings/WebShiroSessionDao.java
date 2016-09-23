package com.sjw.ShiroTest.Settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

public class WebShiroSessionDao extends AbstractSessionDAO {
	@Autowired
    private RedisTemplate<String, String> template;
	
	@Resource(name="redisTemplate")
    private ValueOperations<Serializable,Session> valOps;
	
	@Resource(name="redisTemplate")
	private SetOperations<String,Serializable> setOps;
	
	private final String key = "SessionList";
	private Logger logger = LoggerFactory.getLogger(WebShiroSessionDao.class);

	@Override
	public void update(Session session) throws UnknownSessionException {
		if(session != null && session.getId() != null){
        	Long timeout = session.getTimeout()/1000;
			valOps.set(session.getId(),session, timeout);
		}
		else
			logger.error("Session is empty");
		
	}

	@Override
	public void delete(Session session) {
		if(session.getId() !=null){
			valOps.getOperations().delete(session.getId());
			setOps.remove(key, session.getId());
		}
		else
			logger.error("Session id can't be found");
	}

	@Override
	public Collection<Session> getActiveSessions() {
		List<Session> sList = new ArrayList<Session>();
		Iterator<Serializable> i = setOps.members(key).iterator();
		while(i.hasNext()){
			Session s = valOps.get(i);
			sList.add(s);
			i.next();
		}
		return sList;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);
        if(session != null && session.getId() != null){
        	valOps.set(session.getId(),session);
        	valOps.getOperations().expire(session.getId(), session.getTimeout(), TimeUnit.MILLISECONDS);
			setOps.add(key,session.getId());
		}
        else
        	logger.error("Session is empty");
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return valOps.get(sessionId);
	}

}
