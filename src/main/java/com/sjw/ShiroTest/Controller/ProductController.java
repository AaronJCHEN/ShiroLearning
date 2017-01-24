package com.sjw.ShiroTest.Controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sjw.ShiroTest.Enhance.BrowseNumEnhance;
import com.sjw.ShiroTest.Pojo.BrowsePojo;
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
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired

	
	@Resource(name="redisTemplate")
    private HashOperations<String,String,List> hashOps;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable int id,
										 HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		ProductPojo thisPdct = productService.getProductDetailService(id);

		BrowsePojo browse = new BrowsePojo();
		browse.setProductId(id);
		browse.setUsername(String.valueOf(request.getSession().getAttribute("username")));
		BrowseNumEnhance enhance = new BrowseNumEnhance(browse,productService);
		enhance.run();

		//TODO Better to change USERNAME COLUMN TO USERID COLUMN
		int sum = productService.getBrowseTimesService(thisPdct.getId());

		//move the recent record(name+id) to cookies. Get info from cache
		List<Map> his = productService.updateReadHistory(thisPdct, request,response);

		mv.addObject("pdct",thisPdct);
		mv.addObject("recent",his);
		mv.addObject("hisLen",his.size()-1);
		mv.addObject("browseTimes",sum);
		mv.setViewName("product.definition");
		return mv;
	}
}
