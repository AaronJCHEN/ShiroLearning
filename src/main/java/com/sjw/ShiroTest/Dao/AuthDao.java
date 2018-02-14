package com.sjw.ShiroTest.Dao;

import java.util.List;

import com.sjw.ShiroTest.Pojo.UserPojo;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface AuthDao {
	void registerUser(UserPojo user);
	void registerRoles(UserPojo user);
	List<String> getRoleList(String username);
	List<String> getPermissionList(String roleName);
	UserPojo getUserByName(String username);
	String getJWTPassword(String username);
	UserPojo verifyUser(UserPojo user);
}
