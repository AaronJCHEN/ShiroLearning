package com.sjw.ShiroTest.Settings.Shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.comparator.BooleanComparator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class SessionForRedisFactory extends SimpleSessionFactory{
	private Logger logger = LoggerFactory.getLogger(SessionForRedisFactory.class);

	@Resource(name = "redisTemplate")
	private ValueOperations<String,Session> valOps;

	@Resource(name = "redisTemplate")
	private SetOperations<String,String> setOps;

	private String key;

	@Override
	public Session createSession(SessionContext initData) {
		HttpServletRequest request = (HttpServletRequest) initData
				.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
		String ip = this.getRequestIP(request);
		Boolean isExisted = setOps.isMember(key,ip);
		if (isExisted){
			Session ss = valOps.get(ip);
			if (ss != null)
				return ss;
			else {
				logger.error("The session in the redis's value list isn't existed");
				if (ip != null) {
					return new SessionForRedis(ip);
				}
				return new SessionForRedis();
			}
		}
		else {
			if (ip != null) {
				return new SessionForRedis(ip);
			}
			return new SessionForRedis();
		}
	}

	private String getRequestIP(HttpServletRequest request){
		String localIP = "127.0.0.1";
		String ip = request.getHeader("x-forwarded-for");
		if ("".equals(ip) || ip == null || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ("".equals(ip) || ip == null || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ("".equals(ip) || ip == null || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if("0:0:0:0:0:0:0:1".equals(ip))
		    ip = localIP;
		return ip;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
