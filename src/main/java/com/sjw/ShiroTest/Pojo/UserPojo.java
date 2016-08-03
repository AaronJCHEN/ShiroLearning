package com.sjw.ShiroTest.Pojo;

import java.util.Date;

public class UserPojo {
	private int id;
	private String username;
	private String password;
	private boolean access;
	private Date create_date;
	private Date modified_date;
	private String role;
	private int access_level;
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getAccess_level() {
		return access_level;
	}
	public void setAccess_level(int access_level) {
		this.access_level = access_level;
	}
	
}
