package com.sjw.ShiroTest.Utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Watson on 10/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml","classpath:testServlet-context.xml"})
public class TestRedisMsgSender {
    @Autowired
    RedisMsgSender redisMsgSender;

    @Test
    public void run(){
        String channel = "ShiroSession";
        String msg = "aaa";
        Assert.assertTrue(redisMsgSender.sender(channel,msg));
    }
}
