package com.sjw.ShiroTest.Dao;

import java.util.List;

import com.sjw.ShiroTest.Pojo.UserPojo;

public interface AuthDao {
	public void registerUserDao(UserPojo user);
	public void registerRolesDao(UserPojo user);
	public List<String> getRoleListDao(String username);
	public List<String> getPermissionListDao(String roleName);
	public List<String> getPasswordDao(String username);
}
