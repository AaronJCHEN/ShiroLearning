package com.sjw.ShiroTest.Settings;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SimpleSessionFactory;

public class SessionForRedisFactory extends SimpleSessionFactory {

	@Override
	public Session createSession(SessionContext initData) {
		if (initData != null) {
			String host = initData.getHost();
			if (host != null) {
				return new SessionForRedis(host);
			}
		}
		return new SessionForRedis();
	}

}
