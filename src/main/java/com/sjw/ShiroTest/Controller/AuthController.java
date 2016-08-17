package com.sjw.ShiroTest.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;
import com.sjw.ShiroTest.Utils.RoleType;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthService authService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginUser(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login.definition");
		return mv;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView loginUserForm(@ModelAttribute UserPojo user){
		ModelAndView mv = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
			subject.login(token);
			if(subject.isAuthenticated()){
				mv.addObject("username", user.getUsername());
				mv.setViewName("index.definition");
			}
			else
				mv.setViewName("login.definition");
		}
		else{
			System.out.print("Has accessed");
		}
		return mv;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUserForm(@ModelAttribute UserPojo user){
		ModelAndView mv = new ModelAndView();
		user.setPassword(new Md5Hash(user.getPassword()).toHex());
		List<String> roles = new ArrayList<String>();
		roles.add("USER");
		user.setRoles(roles);
		user.setCreate_date(new Date());
		user.setModified_date(new Date());
		authService.registerUserService(user);
		authService.registerRolesService(user);
		mv.setViewName("index.definition");
		return mv;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ModelAndView logoutUser(){
		ModelAndView mv = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		mv.setViewName("login.definition");
		return mv;
	}
	
	

}
