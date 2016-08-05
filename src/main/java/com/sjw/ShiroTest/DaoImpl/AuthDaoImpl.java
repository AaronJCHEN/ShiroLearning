package com.sjw.ShiroTest.DaoImpl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;

@Repository
public class AuthDaoImpl implements AuthDao {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void registerUserDao(UserPojo user) {
		this.sqlSession.insert("createUser",user);
	}

}
