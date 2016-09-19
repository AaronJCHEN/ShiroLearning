package com.sjw.ShiroTest.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Pojo.UserPojo;
import com.sjw.ShiroTest.Service.AuthService;
import com.sjw.ShiroTest.Service.ProductService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthService authService;
	
	@Autowired
	ProductService productService;

	private Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginUser(){
		ModelAndView mv = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        //If using the rememberMe to login, the Authentication is false. Vice visa
        if (subject.isRemembered()||subject.isAuthenticated()){
            String username = subject.getPrincipal().toString();
            subject.getSession().setAttribute("username",username);
            List<ProductPojo> rproducts = productService.getRecommendedProductsService();
            mv.addObject("rproducts", rproducts);
            mv.setViewName("index.definition");
        }
        else
		    mv.setViewName("login");
		return mv;
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView loginUserForm(@ModelAttribute UserPojo user){
		ModelAndView mv = new ModelAndView();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        if(user.getRememberMe()!=null && user.getRememberMe())
            token.setRememberMe(true);
        else
            token.setRememberMe(false);
		Subject subject = SecurityUtils.getSubject();
		try{
			subject.login(token);
			if(subject.isAuthenticated()){
				Session session = subject.getSession();
				session.setAttribute("username", user.getUsername());
				List<ProductPojo> rproducts = productService.getRecommendedProductsService();
				mv.addObject("rproducts", rproducts);
				mv.setViewName("index.definition");
			}
			else
				mv.setViewName("login");
		}
		catch (AuthenticationException e){
			log.error("Login Error",e);
			mv.setViewName("login");
		}

		return mv;
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
			mv.setViewName("login");
		}

		return mv;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ModelAndView logoutUser(){
		ModelAndView mv = new ModelAndView();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		mv.setViewName("login");
		return mv;
	}

}
