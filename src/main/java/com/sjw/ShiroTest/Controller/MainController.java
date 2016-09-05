package com.sjw.ShiroTest.Controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjw.ShiroTest.Settings.RealmForShiroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Utils.RoleType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/index")
public class MainController {
	@Autowired
	RealmForShiroDao realmForShiroDao;
	
	@RequestMapping(value="/editRole")
	public ModelAndView manageRoles(HttpServletRequest request) throws JsonProcessingException{
		ModelAndView mv = new ModelAndView();
		String[] roleType = new String[RoleType.values().length];
		for(RoleType r : RoleType.values()){
			roleType[r.ordinal()] = r.getRole();
		}
		mv.addObject("roleType",roleType);

		//Search My roles
		HttpSession session = request.getSession();
		List<String> role_list = realmForShiroDao.getRoleList(session.getAttribute("username").toString());
		ObjectMapper objMapper = new ObjectMapper();
		mv.addObject("myRoles", objMapper.writeValueAsString(role_list));
		
		mv.setViewName("profile.definition");
		return mv;
	}

}
