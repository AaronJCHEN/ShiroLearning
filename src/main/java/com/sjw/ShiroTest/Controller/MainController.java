package com.sjw.ShiroTest.Controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Utils.RoleType;

@Controller
@RequestMapping("/index")
public class MainController {
	
	@RequestMapping(value="/editRole")
	public ModelAndView manageRoles(){
		ModelAndView mv = new ModelAndView();
		String[] roleType = new String[RoleType.values().length];
		for(RoleType r : RoleType.values()){
			roleType[r.ordinal()] = r.getRole();
		}
		mv.addObject("roleType",roleType);
		mv.setViewName("profile.definition");
		return mv;
	}

}
