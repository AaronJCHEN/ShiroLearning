package com.sjw.ShiroTest.Settings;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sjw.ShiroTest.Service.AuthService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml","classpath:testServlet-context.xml"
		,"classpath:testSpring-shiro.xml"})
public class TestRealmForShiro {
	@Autowired
	AuthService authService;
	
	@Test
	public void run() throws SQLException{
		authService.getRoleListService("aaa");
	}
}
