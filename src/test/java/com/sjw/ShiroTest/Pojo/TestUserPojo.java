package com.sjw.ShiroTest.Pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TestUserPojo implements Serializable {
	private static final long serialVersionUID = -7898194272883238670L;  

	public static final String OBJECT_KEY = "USER";
    
    private int id;
	private String username;
	private String password;
	private boolean access;
	private Date create_date;
	private Date modified_date;
	private List<String> roles;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAccess() {
		return access;
	}
	public void setAccess(boolean access) {
		this.access = access;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getModified_date() {
		return modified_date;
	}
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public static String getObjectKey() {
		return OBJECT_KEY;
	}
	@Override
	public String toString() {
		return "TestUserPojo [id=" + id + ", username=" + username + ", access=" + access + ", create_date="
				+ create_date + ", modified_date=" + modified_date + ", roles=" + roles + "]";
	}
}
