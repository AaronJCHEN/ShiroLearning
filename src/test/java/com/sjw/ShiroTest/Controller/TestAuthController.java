package com.sjw.ShiroTest.Controller;

import com.sjw.ShiroTest.Config.TestEhCacheConfig;
import com.sjw.ShiroTest.Config.TestShiroConfig;
import com.sjw.ShiroTest.Config.TestSpringConfig;
import com.sjw.ShiroTest.Config.TestSpringSessionConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Created by sjw on 16/8/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestAuthController {
	private Logger logger = LoggerFactory.getLogger(TestAuthController.class);

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
		SecurityUtils.setSecurityManager(securityManager);

//		SecurityManager securityManager = mock(SecurityManager.class, RETURNS_DEEP_STUBS);
//		ThreadContext.bind(securityManager);
	}
	
    @Test
    public void run() throws Exception {
		mockMvc.perform(post("/auth/login")
				.session(session)
				.param("username","qy@163.com")
				.param("password","qyqy"))
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
    }

}