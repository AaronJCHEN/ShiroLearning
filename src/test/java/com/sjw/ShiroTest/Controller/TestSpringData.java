package com.sjw.ShiroTest.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sjw.ShiroTest.Pojo.TestUserPojo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:testApplicationContext.xml","classpath:testServlet-context.xml"})
public class TestSpringData {
	private final Logger logger = LoggerFactory.getLogger(TestSpringData.class);
	
	// inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;
    
    @Resource(name="redisTemplate")
    private HashOperations<String,String,TestUserPojo> hashOps;
    
    @Test
    public void run(){
    	TestUserPojo user = new TestUserPojo();
    	user.setUsername("sjw");
    	user.setPassword("aaa");
    	List<String> roles = new ArrayList<String>();
		roles.add("USER");
		user.setRoles(roles);
		user.setCreate_date(new Date());
		user.setModified_date(new Date());
		hashOps.put(user.getObjectKey(), user.getUsername(), user);
    	logger.debug(hashOps.get(user.getObjectKey(), user.getUsername()).toString());
    	user.setUsername("qyqy");
    	hashOps.put(user.getObjectKey(), user.getUsername(), user);
    	logger.debug(hashOps.get(user.getObjectKey(), user.getUsername()).toString());
    }
}
