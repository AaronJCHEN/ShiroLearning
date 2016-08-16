package com.sjw.ShiroTest.DaoImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sjw.ShiroTest.Dao.AuthDao;
import com.sjw.ShiroTest.Pojo.UserPojo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:servlet-context.xml"})
public class testAuthDaoImpl {
	@Autowired
	AuthDao authDao;
	
	
	@Test
	public void testRegisterRolesDao(){
		UserPojo user = new UserPojo();
		user.setUsername("abc");
		List<String> roles = new ArrayList<String>();
		user.setRoles(roles);
		authDao.registerRolesDao(user);
	}
}
