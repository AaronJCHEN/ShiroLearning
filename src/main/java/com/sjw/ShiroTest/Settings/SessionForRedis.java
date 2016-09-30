package com.sjw.ShiroTest.Settings;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.mgt.SimpleSession;

public class SessionForRedis extends SimpleSession {

	private static final long serialVersionUID = -7136803329764600351L;
	
	private boolean needUpdate;
	private Date deadline;
	
	/*Add a logic. When current date is over the timeout, update the redis side and notified
	all tomcat server to update session*/
	
	public SessionForRedis() {
		super();
		this.needUpdate = true;
	}

	public SessionForRedis(String host) {
		super(host);
		this.needUpdate = true;
	}
	
	@Override
	public void setId(Serializable id) {
		super.setId(id);
		this.needUpdate = true;
	}

	@Override
	public void setStartTimestamp(Date startTimestamp) {
		super.setStartTimestamp(startTimestamp);
		this.needUpdate = true;
	}

	@Override
	public void setStopTimestamp(Date stopTimestamp) {
		super.setStopTimestamp(stopTimestamp);
		this.needUpdate = true;
	}

	@Override
	public void setLastAccessTime(Date lastAccessTime) {
		super.setLastAccessTime(lastAccessTime);
		this.needUpdate = false;
	}

	@Override
	public void setExpired(boolean expired) {
		super.setExpired(expired);
		this.needUpdate = true;
	}

	@Override
	public void setTimeout(long timeout) {
		super.setTimeout(timeout);
		this.needUpdate = true;
	}

	@Override
	public void setHost(String host) {
		super.setHost(host);
		this.needUpdate = true;
	}

	@Override
	public void setAttributes(Map<Object, Object> attributes) {
		super.setAttributes(attributes);
		this.needUpdate = true;
	}
	
	@Override
	public void touch() {
		super.touch();
		this.needUpdate = false;
	}

	@Override
	public void stop() {
		super.stop();
		this.needUpdate = true;
	}

	@Override
	protected void expire() {
		super.expire();
		this.needUpdate = true;
	}

	@Override
	public void setAttribute(Object key, Object value) {
		super.setAttribute(key, value);
		this.needUpdate = true;
	}

	@Override
	public Object removeAttribute(Object key) {
		this.needUpdate = true;
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
	
	
	
}
