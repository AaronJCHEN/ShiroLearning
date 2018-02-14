package com.sjw.ShiroTest.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sjw.ShiroTest.Pojo.JWTTokenPojo;
import com.sjw.ShiroTest.Pojo.ResponsePojo;
import com.sjw.ShiroTest.Utils.JWTUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@Autowired
	JWTUtils jwtUtils;

	private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
	public UserPojo getUserByNameService(String username) {
		return authDao.getUserByName(username);
	}

	@Override
	public UserPojo verifyUserService(UserPojo user) { return authDao.verifyUser(user); }

	@Override
	public ResponsePojo loginUserFormService(UserPojo user) {
		UserPojo userVerified = authDao.verifyUser(user);
		if (userVerified != null){
			JWTTokenPojo jwtToken = new JWTTokenPojo(jwtUtils.create(userVerified.getUsername()));
			Subject subject = SecurityUtils.getSubject();
			try {
				subject.login(jwtToken);
				if(subject.isAuthenticated()) {
					Map<String, String> obj = new HashMap<>();
					obj.put("token",jwtToken.getToken());
					obj.put("username",userVerified.getUsername());
					ResponsePojo result = new ResponsePojo(HttpStatus.OK.value(),"Success",obj);
					return result;
				}
			}
			catch (AuthenticationException e) {
				logger.error("Login Error", e);
			}
			throw new RuntimeException("Error");
		}
		else{
			ResponsePojo result = new ResponsePojo(HttpStatus.UNAUTHORIZED.value(),"Failure, password error");
			return result;
		}
	}

}
