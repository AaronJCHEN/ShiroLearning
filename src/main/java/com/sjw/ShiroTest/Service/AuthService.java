package com.sjw.ShiroTest.Service;

import java.util.List;

import com.sjw.ShiroTest.Pojo.UserPojo;

public interface AuthService {
	public void registerUserService(UserPojo user);
	public void registerRolesService(UserPojo user);
	public List<String> getRoleListService(String username);
	public List<String> getPermissionListService(String roleName);
	public List<String> getPasswordService(String username);
}
