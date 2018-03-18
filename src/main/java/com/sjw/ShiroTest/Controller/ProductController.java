package com.sjw.ShiroTest.Controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sjw.ShiroTest.Enhance.BrowseNumEnhance;
import com.sjw.ShiroTest.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public List getCommentsByProduct(HttpServletRequest request) throws JsonProcessingException {
		int productId = Integer.parseInt(request.getParameter("productId"));
		int pageNum = 1;
		if(request.getParameter("pageNum") != null)
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		Map<String,Integer> params = new HashMap<>();
		int offset = (pageNum-1)*10;
		params.put("productId",productId);
		params.put("offset",offset);
		List<CommentPojo> comments = productService.getCommentsByProductService(params);
		return comments;
	}

	@RequestMapping(value = "/recommended",method = RequestMethod.GET)
	public List<ProductPojo> getRecommendedProducts(){
		return productService.getRecommendedProductsService();
	}
}
