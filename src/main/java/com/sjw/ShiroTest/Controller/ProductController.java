package com.sjw.ShiroTest.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Service.ProductService;

@Controller
@RequestMapping(value="product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Resource(name="redisTemplate")
    private HashOperations<String,String,List> hashOps;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable int id,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		ProductPojo thisPdct = productService.getProductDetailService(id);
		//Update browse num
		
		
		//Update recent browse record
		List<Map> recent = hashOps.get("recent", request.getSession().getAttribute("username"));
		Map<String,String> his = new HashMap<String,String>();
		if(recent == null){
			recent = new LinkedList<Map>();
		}
		else{
			Iterator i = recent.iterator();
			Boolean isFind = false;
			while(i.hasNext()){
				Map<String,String> oprod = (Map<String, String>) i.next();
				if(oprod.get("name").equals(thisPdct.getName())){
					recent.remove(recent.indexOf(oprod));
					isFind = true;
					break;
				}
			}
			if(!isFind){
				recent.remove(recent.size()-1);
			}
		}
		his.put("id", String.valueOf(thisPdct.getId()));
		his.put("name", thisPdct.getName());
		his.put("date", new Date().toString());
		recent.add(0, his);
		hashOps.put("recent", request.getSession().getAttribute("username").toString(), recent);
		mv.addObject("recent",recent);
		mv.addObject("pdct",thisPdct);
		mv.setViewName("product.definition");
		return mv;
	}
}
