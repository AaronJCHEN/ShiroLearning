package com.sjw.ShiroTest.Controller;

import java.util.*;

import com.sjw.ShiroTest.Pojo.JWTTokenPojo;
import com.sjw.ShiroTest.Pojo.ResponsePojo;
import com.sjw.ShiroTest.Service.MainService;
import com.sjw.ShiroTest.Utils.JWTUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;
import com.sjw.ShiroTest.Service.ProductService;

@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping("/ShiroTest/auth")
public class AuthController{
	@Autowired
	AuthService authService;

	private Logger log = LoggerFactory.getLogger(AuthController.class);


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponsePojo loginUserForm(@ModelAttribute UserPojo user){
		user.setPassword(new Md5Hash(user.getPassword().getBytes()).toString());
		ResponsePojo response = authService.loginUserFormService(user);
		return response;
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
