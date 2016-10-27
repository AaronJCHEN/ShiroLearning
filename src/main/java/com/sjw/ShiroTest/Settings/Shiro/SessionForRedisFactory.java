package com.sjw.ShiroTest.Settings.Shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;

import javax.servlet.http.HttpServletRequest;

public class SessionForRedisFactory extends SimpleSessionFactory {

	@Override
	public Session createSession(SessionContext initData) {
		HttpServletRequest request = (HttpServletRequest) initData
				.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
		String ip = this.getRequestIP(request);
		if (ip != null) {
				return new SessionForRedis(ip);
		}
		return new SessionForRedis();
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

}
