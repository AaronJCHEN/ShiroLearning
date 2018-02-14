package com.sjw.ShiroTest.Utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestJWTUtils {
    @Autowired
    JWTUtils jwtUtils;

    @Test
    public void run(){
        String result = jwtUtils.create("qyqy@163.com");
        Assert.assertNotNull(result);
    }
}
