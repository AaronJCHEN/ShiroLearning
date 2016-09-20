package com.sjw.ShiroTest.DaoImpl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;

public class AuthDaoImpl extends SqlSessionDaoSupport implements AuthDao{
	
	@Override
	public void registerUserDao(UserPojo user){
		this.getSqlSession().insert("createUser",user);
	}
	@Override
	public void registerRolesDao(UserPojo user){
		this.getSqlSession().insert("createUserRole", user);
		
	}
	@Override
	public List<String> getRoleListDao(String username){
		return this.getSqlSession().selectList("getRoles4User", username);
	}
	
	@Override
	public List<String> getPermissionListDao(String roleName){
		return this.getSqlSession().selectList("getPmion4User", roleName);
	}
	
	@Override
	public List<String> getPasswordDao(String username){
		return this.getSqlSession().selectList("getPswd4User",username);
	}

}
