package com.sjw.ShiroTest.Settings.Shiro;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionForRedis extends SimpleSession {

	private static final long serialVersionUID = -7136803329764600351L;
	private Logger logger = LoggerFactory.getLogger(SessionForRedis.class);
	
	private boolean needUpdate;
	private boolean needNotified; //Prepared
	private Date deadline;
	
	/*Add a logic. When current date is over the timeout, update the redis side and notified
	all tomcat server to update session*/
	
	public SessionForRedis() {
		super();
		logger.info("Session for Redis is created");
		this.needUpdate = true;
		this.needNotified = false;
	}

	public SessionForRedis(String host) {
		super(host);
		logger.info("Session for Redis is created by host");
		this.needUpdate = true;
		this.needNotified = false;
	}
	
	@Override
	public void setId(Serializable id) {
		super.setId(id);
		logger.info("Session id is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public void setLastAccessTime(Date lastAccessTime) {
		super.setLastAccessTime(lastAccessTime);
		this.needUpdate = false;
		this.needNotified = false;
	}

	@Override
	public void setStartTimestamp(Date startTimestamp) {
		super.setStartTimestamp(startTimestamp);
		logger.info("Session startTimestamp is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public void setStopTimestamp(Date stopTimestamp) {
		super.setStopTimestamp(stopTimestamp);
		logger.info("Session stopTimestamp is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public void setExpired(boolean expired) {
		super.setExpired(expired);
		logger.info("Session expired is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public boolean isExpired() {
		return super.isExpired();
	}

	@Override
	protected boolean isStopped() {
		return super.isStopped();
	}

	@Override
	public void setTimeout(long timeout) {
		super.setTimeout(timeout);
		logger.info("Session timeout is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public void setHost(String host) {
		super.setHost(host);
		logger.info("Session host is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public void setAttributes(Map<Object, Object> attributes) {
		super.setAttributes(attributes);
		logger.info("Session attributes is set");
		this.needUpdate = true;
		this.needNotified = false;
	}
	
	@Override
	public void touch() {
		super.touch();
		logger.info("Session is touched");
		this.needUpdate = false;
		this.needNotified = false;
	}

	@Override
	public void stop() {
		super.stop();
		logger.info("Session stop is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	protected void expire() {
		super.expire();
		logger.info("Session expire is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public void setAttribute(Object key, Object value) {
		super.setAttribute(key, value);
		logger.info("Session attribute is set");
		this.needUpdate = true;
		this.needNotified = false;
	}

	@Override
	public Object removeAttribute(Object key) {
		this.needUpdate = true;
		this.needNotified = false;
		logger.info("Session attribute is removed");
		return super.removeAttribute(key);
	}

	@Override
	protected boolean onEquals(SimpleSession ss) {
		// TODO Auto-generated method stub
		return super.onEquals(ss);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}


	public boolean isNeedNotified() {
		return needNotified;
	}

	public void setNeedNotified(boolean needNotified) {
		this.needNotified = needNotified;
	}
}
