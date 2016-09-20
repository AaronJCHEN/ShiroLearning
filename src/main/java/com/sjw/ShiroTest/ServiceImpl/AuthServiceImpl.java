package com.sjw.ShiroTest.ServiceImpl;

import java.util.List;

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

	@Override
	public List<String> getRoleListService(String username) {
		return authDao.getRoleListDao(username);
	}

	@Override
	public List<String> getPermissionListService(String roleName) {
		return authDao.getPermissionListDao(roleName);
	}

	@Override
	public List<String> getPasswordService(String username) {
		return authDao.getPasswordDao(username);
	}

}
