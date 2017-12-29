package com.sjw.ShiroTest.Controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjw.ShiroTest.Service.AuthService;
import com.sjw.ShiroTest.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Utils.RoleType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping("/ShiroTest/index")
public class MainController {
	@Autowired
	AuthService authService;

	@Autowired
	MainService mainService;

	public ModelAndView initIndex(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("index.definition");
		return mv;
	}

	@RequestMapping(value="/profile")
	public List<Map> manageRoles(HttpServletRequest request) throws JsonProcessingException{
		HttpSession session = request.getSession();
		List<String> role_list = authService.getRoleListService(session.getAttribute("username").toString());
		List<Map> roleList = new ArrayList<>();
		for(RoleType r : RoleType.values()) {
			Map<String,Object> roleMap = new HashMap<>();
			if (role_list.contains(r.getRole())){
				roleMap.put("role", r.getRole());
				roleMap.put("isChecked", true);
			}
			else {
				roleMap.put("role", r.getRole());
				roleMap.put("isChecked", false);
			}
			roleList.add(roleMap);
		}
		return roleList;
	}

	@RequestMapping(value = "/getMainMenu",method = RequestMethod.GET)
	public List<Map> getMainMenu(){
		return mainService.getMenuService();
	}

	@RequestMapping(value="/getSubMenu",method = RequestMethod.POST)
	public List<Map> getSubMenu(HttpServletRequest request) throws JsonProcessingException {
		int id_int = Integer.parseInt(request.getParameter("mainMenu"));
		List<Map> subMenu= mainService.getSubMenuService(id_int);
		return subMenu;
	}

}
