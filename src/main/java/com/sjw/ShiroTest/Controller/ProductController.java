package com.sjw.ShiroTest.Controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sjw.ShiroTest.Msg.RedisMsgSender;
import com.sjw.ShiroTest.Pojo.MsgWrapperPojo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
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
	
	@Resource(name="redisTemplate")
    private HashOperations<String,String,List> hashOps;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable int id,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		ProductPojo thisPdct = productService.getProductDetailService(id);
		//TODO Update browse num on database
		synchronized (this){
			Double browse_num = thisPdct.getBrowse_num();
        }

		//move the recent record from redis to local cache in order to release the burden on redis
		productService.updateReadHistory(thisPdct,request.getSession().getAttribute("username").toString());

		mv.addObject("pdct",thisPdct);
		mv.setViewName("product.definition");
		return mv;
	}
}
