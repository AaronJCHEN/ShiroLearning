package com.sjw.ShiroTest.Controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sjw.ShiroTest.Enhance.BrowseNumEnhance;
import com.sjw.ShiroTest.Pojo.BrowsePojo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Service.ProductService;

@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping(value="/ShiroTest/product")
public class ProductController {
	@Autowired
	ProductService productService;

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Map getProductDetail(@PathVariable int id,
										 HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		ProductPojo thisPdct = productService.getProductDetailService(id);

		BrowsePojo browse = new BrowsePojo();
		browse.setProductId(id);
		browse.setUsername(String.valueOf(request.getSession().getAttribute("username")));
		BrowseNumEnhance enhance = new BrowseNumEnhance(browse,productService);
		enhance.run();

		int sum = productService.getBrowseTimesService(thisPdct.getId());

		//move the recent record(name+id) to cookies. Get info from cache
		List<Map> his = productService.updateReadHistory(thisPdct, request,response);

		Map<String,Object> pdctDetail = new HashMap();
		pdctDetail.put("pdct",thisPdct);
		pdctDetail.put("recent",his);
		pdctDetail.put("browseTimes",sum);
		return pdctDetail;

	}

	@RequestMapping(value = "/recommended",method = RequestMethod.GET)
	public List<ProductPojo> getRecommendedProducts(){
		return productService.getRecommendedProductsService();
	}
}
