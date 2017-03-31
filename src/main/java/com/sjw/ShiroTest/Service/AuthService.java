package com.sjw.ShiroTest.Service;

import java.util.List;

import com.sjw.ShiroTest.Pojo.UserPojo;

public interface AuthService {
	void registerUserService(UserPojo user);
	void registerRolesService(UserPojo user);
	List<String> getRoleListService(String username);
	List<String> getPermissionListService(String roleName);
	List<String> getPasswordService(String username);
	UserPojo getUserByNameService(String username);
}
