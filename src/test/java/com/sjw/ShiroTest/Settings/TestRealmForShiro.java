package com.sjw.ShiroTest.Settings;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml","classpath:testServlet-context.xml"})
public class TestRealmForShiro {
	@Autowired
	RealmForShiroDao realmForShiroDao;
	
	@Test
	public void run() throws SQLException{
		realmForShiroDao.getRoleList("aaa");
	}
}
