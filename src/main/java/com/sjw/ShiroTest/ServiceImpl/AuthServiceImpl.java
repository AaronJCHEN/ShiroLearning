package com.sjw.ShiroTest.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;

@Service
@Transactional(rollbackFor=Exception.class)
public class AuthServiceImpl implements AuthService {
	@Autowired
	AuthDao authDao;

	@Override
	public void registerUserService(UserPojo user){
		authDao.registerUserDao(user);
	}

	@Override
	public void registerRolesService(UserPojo user){
		authDao.registerRolesDao(user);
	}

}
