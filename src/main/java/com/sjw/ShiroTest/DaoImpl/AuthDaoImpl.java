package com.sjw.ShiroTest.DaoImpl;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;

public class AuthDaoImpl extends SqlSessionDaoSupport implements AuthDao{
	
	@Override
	public void registerUserDao(UserPojo user){
		this.getSqlSession().insert("createUser",user);
		String string  = null;
	    if(string.equals("")) {
	        int i = 0;
	    }
		
	}
	@Override
	public void registerRolesDao(UserPojo user){
		this.getSqlSession().insert("createUserRole", user);
		
	}

}
