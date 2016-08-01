package com.sjw.ShiroTest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/index")
public class MainController {
	@RequestMapping(value="/init" , method = RequestMethod.GET)
	public ModelAndView indexInit(){
		System.out.println("aaa");
		return null;
	}

}
