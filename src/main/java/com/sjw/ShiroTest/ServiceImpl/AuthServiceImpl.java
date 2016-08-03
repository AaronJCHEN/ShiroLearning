package com.sjw.ShiroTest.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	AuthDao authDao;

	@Override
	public void registerService(UserPojo user) {
		authDao.registerUserDao(user);
	}

}
