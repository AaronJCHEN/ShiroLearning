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

	@Autowired
	CacheManager cacheManager;

	@Autowired
	RedisMsgSender redisMsgSender;
	
	@Resource(name="redisTemplate")
    private HashOperations<String,String,List> hashOps;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable int id,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		ProductPojo thisPdct = productService.getProductDetailService(id);
		//TODO Update browse num, placed also on local cache and sync by redis publish and subscribe system
		
		
		//Update recent browse record
		//TODO move the recent record from redis to local cache in order to release the burden on redis
		Cache readHistory = cacheManager.getCache("read-history");
		Element r = readHistory.get(request.getSession().getAttribute("username"));

		Object readList = r.getObjectValue();
		if(readList == null){
			List<ProductPojo> historyList = new ArrayList<>();
			historyList.add(thisPdct);
			Element thisReadList = new Element(request.getSession().getAttribute("username").toString(),historyList);
			readHistory.put(thisReadList);
		}
		else{
			if (readList instanceof List){
				List<ProductPojo> historyList = (List<ProductPojo>) readList;
				if(historyList.size()>10)
					throw new RuntimeException("The Cache size goes wrong");
				else{
					if (historyList.size() == 10)
						historyList.remove(0);

					historyList.add(thisPdct);
				}
			}
			else
				throw new RuntimeException("The Cache type goes wrong");
		}

		MsgWrapperPojo<ProductPojo> msgWrapper = new MsgWrapperPojo<>("history-update",thisPdct);

		redisMsgSender.sender("product-update",msgWrapper);

/*		List<Map> recent = hashOps.get("recent", request.getSession().getAttribute("username"));
		Map<String,String> his = new HashMap<>();
		if(recent == null){
			recent = new LinkedList<>();
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
		hashOps.put("recent", request.getSession().getAttribute("username").toString(), recent);*/
		mv.addObject("pdct",thisPdct);
		mv.setViewName("product.definition");
		return mv;
	}
}
