package com.sjw.ShiroTest.Service;

import com.sjw.ShiroTest.Pojo.UserPojo;

public interface AuthService {
	public void registerUserService(UserPojo user);
	public void registerRolesService(UserPojo user);
}
