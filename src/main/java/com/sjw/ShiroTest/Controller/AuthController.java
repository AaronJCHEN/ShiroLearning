package com.sjw.ShiroTest.Controller;

import java.util.*;

import com.sjw.ShiroTest.Service.MainService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;
import com.sjw.ShiroTest.Service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping("/ShiroTest/auth")
public class AuthController{
	@Autowired
	AuthService authService;
	
	@Autowired
	ProductService productService;

	@Autowired
	MainService mainService;

	private Logger log = LoggerFactory.getLogger(AuthController.class);


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String,Object> loginUserForm(@ModelAttribute UserPojo user){
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
		if(user.getRememberMe()!=null && user.getRememberMe())
			token.setRememberMe(true);
		else
			token.setRememberMe(false);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			if(subject.isAuthenticated()) {
				Session session = subject.getSession();
				session.setAttribute("username", user.getUsername());
				session.setAttribute("userId",user.getId());
				log.info(user.getUsername()+" has logged in");
				Map<String, Object> result = new HashMap<>();
				result.put("token",token);
				result.put("jSessionId",session.getId());
				return result;
			}
		}
		catch (AuthenticationException e) {
			log.error("Login Error", e);
		}
		throw new RuntimeException("Error");
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUserForm(@ModelAttribute UserPojo user) throws Exception{
		ModelAndView mv = new ModelAndView();
		String original_password = user.getPassword();
		user.setPassword(new Md5Hash(original_password).toHex());
		List<String> roles = new ArrayList<String>();
		roles.add("USER");
		user.setRoles(roles);
		user.setCreate_date(new Date());
		user.setModified_date(new Date());
		authService.registerUserService(user);
		authService.registerRolesService(user);
		//Get Authentication
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),original_password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		if(subject.isAuthenticated()){
			Session session = subject.getSession();
			session.setAttribute("username", user.getUsername());
			mv.setViewName("index.definition");
		}
		else{
			mv.setViewName("loginPage");
		}

		return mv;
	}

	@ResponseBody
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutUser() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "success";
	}

}
