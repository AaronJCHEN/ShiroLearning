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
		authDao.registerUser(user);
	}

	@Override
	public void registerRolesService(UserPojo user){
		authDao.registerRoles(user);
	}

	@Override
	public List<String> getRoleListService(String username) {
		return authDao.getRoleList(username);
	}

	@Override
	public List<String> getPermissionListService(String roleName) {
		return authDao.getPermissionList(roleName);
	}

	@Override
	public List<String> getPasswordService(String username) {
		return authDao.getPassword(username);
	}

	@Override
	public UserPojo getUserByNameService(String username) {
		return authDao.getUserByName(username);
	}

}
