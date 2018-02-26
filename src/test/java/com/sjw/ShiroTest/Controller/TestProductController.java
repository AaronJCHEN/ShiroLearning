package com.sjw.ShiroTest.Controller;

import com.sjw.ShiroTest.Config.TestEhCacheConfig;
import com.sjw.ShiroTest.Config.TestShiroConfig;
import com.sjw.ShiroTest.Config.TestSpringConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Watson on 01/17/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestEhCacheConfig.class,
        TestShiroConfig.class,
        TestSpringConfig.class})
@WebAppConfiguration
public class TestProductController {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SecurityManager securityManager;

    private MockMvc mockMvc;

    private MockHttpSession session;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.session = new MockHttpSession();
        this.session.setAttribute("username","a");
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void run() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/2")
                .session(session))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.assertEquals("product.definition",result.getModelAndView().getViewName());
    }
}
