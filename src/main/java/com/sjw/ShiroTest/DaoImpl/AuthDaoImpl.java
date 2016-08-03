package com.sjw.ShiroTest.DaoImpl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;

public class AuthDaoImpl extends SqlSessionDaoSupport implements AuthDao {
	
	@Override
	public void registerUserDao(UserPojo user) {
		this.getSqlSession().insert("createUser",user);
	}

}
