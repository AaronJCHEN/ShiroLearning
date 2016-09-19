package com.sjw.ShiroTest.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sjw on 16/8/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml","classpath:testServlet-context.xml"})
public class TestAuthController {
	
	@Autowired
	AuthService authService;
	private Logger logger = LoggerFactory.getLogger(TestAuthController.class);
	
    @Test
    public void run() throws Exception {
	    UserPojo thisUser = new UserPojo();
	    thisUser.setUsername(String.valueOf(1));
	    thisUser.setPassword(String.valueOf(1));
	    thisUser.setCreate_date(new Date());
	    thisUser.setModified_date(new Date());
    	
    	try{
    			authService.registerUserService(thisUser);
    	}
    	catch(RuntimeException e){
    		logger.error("Errors:",e);
    	}
    }

}