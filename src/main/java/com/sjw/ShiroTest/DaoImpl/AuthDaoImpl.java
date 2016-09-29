package com.sjw.ShiroTest.DaoImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;

@Repository
public class AuthDaoImpl implements AuthDao{
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void registerUserDao(UserPojo user){
		this.sqlSession.insert("createUser",user);
	}
	@Override
	public void registerRolesDao(UserPojo user){
		this.sqlSession.insert("createUserRole", user);
		
	}
	@Override
	public List<String> getRoleListDao(String username){
		return this.sqlSession.selectList("getRoles4User", username);
	}
	
	@Override
	public List<String> getPermissionListDao(String roleName){
		return this.sqlSession.selectList("getPmion4User", roleName);
	}
	
	@Override
	public List<String> getPasswordDao(String username){
		return this.sqlSession.selectList("getPswd4User",username);
	}

}
