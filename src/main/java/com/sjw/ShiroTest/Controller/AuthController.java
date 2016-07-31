package com.sjw.ShiroTest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("auth")
public class AuthController {
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView loginUser(){
		return null;
	}

}
