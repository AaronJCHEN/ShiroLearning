package com.sjw.ShiroTest.Controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by sjw on 16/8/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:servlet-context.xml"})
public class TestAuthController {
    @Test
    public void loginUser() throws Exception {

    }

}