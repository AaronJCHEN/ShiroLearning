package com.sjw.ShiroTest.Utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Watson on 10/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml","classpath:testServlet-context.xml"
        ,"classpath:testSpring-shiro.xml"})
public class TestRedisMsgSender {
    @Autowired
    RedisMsgSender redisMsgSender;

    @Autowired
    SecurityManager securityManager;

    @Test
    public void run(){
        //String channel = "ShiroSession";
        //String msg = "aaa";
        //Assert.assertTrue(redisMsgSender.sender(channel,msg));
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        Session s = subject.getSession();
        if(s != null) {
            System.out.print("Success");
            if (s instanceof SimpleSession)
                System.out.print("Session Type is "+subject.getSession().getClass());
        }
        else
            System.out.print("Failure");

    }
}
