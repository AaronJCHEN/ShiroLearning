package com.sjw.ShiroTest.Dao;

import java.util.List;

import com.sjw.ShiroTest.Pojo.UserPojo;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface AuthDao {
	public void registerUser(UserPojo user);
	public void registerRoles(UserPojo user);
	public List<String> getRoleList(String username);
	public List<String> getPermissionList(String roleName);
	public List<String> getPassword(String username);
}
