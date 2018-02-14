package com.sjw.ShiroTest.Service;

import java.util.List;

import com.sjw.ShiroTest.Pojo.ResponsePojo;
import com.sjw.ShiroTest.Pojo.UserPojo;

public interface AuthService {
	void registerUserService(UserPojo user);
	void registerRolesService(UserPojo user);
	List<String> getRoleListService(String username);
	List<String> getPermissionListService(String roleName);
	UserPojo getUserByNameService(String username);
	UserPojo verifyUserService(UserPojo user);
	ResponsePojo loginUserFormService(UserPojo user);
}
