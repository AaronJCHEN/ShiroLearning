package com.sjw.ShiroTest.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sjw.ShiroTest.Msg.RedisMsgSender;
import com.sjw.ShiroTest.Pojo.BrowsePojo;
import com.sjw.ShiroTest.Pojo.MsgWrapperPojo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.ProductDao;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;

	@Override
	public List<ProductPojo> getRecommendedProductsService() {
		return productDao.getRecommendedProducts();
	}

	@Override
	public ProductPojo getProductDetailService(int id) {
		return productDao.getProductDetail(id);
	}

	@Override
	public BrowsePojo getBrowseDetailService(BrowsePojo browse) {
		return productDao.getBrowseDetail(browse);
	}

	@Override
	public void createBrowseRecordService(BrowsePojo browse) {
		productDao.createBrowseRecord(browse);
	}

	@Override
	public void updateBrowseTimesService(BrowsePojo browse) {
		productDao.updateBrowseTimes(browse);
	}

	@Override
	public int getBrowseTimesService(int productId) {
		return productDao.getBrowseTimes(productId);
	}

	@Override
	public List<Map> updateReadHistory(ProductPojo product,
									   HttpServletRequest request, HttpServletResponse response) {
		Boolean isFindCookie = false;
		String username = request.getSession().getAttribute("username").toString();
		Cookie[] cookies = request.getCookies();
		Cookie his = null;
		if (cookies != null){
			for (Cookie cookie:cookies){
				if (cookie.getName().contains(username+"-his")){
					his = cookie;
					isFindCookie = true;
					break;
				}
				else
					continue;
			}
			if (!isFindCookie){
				his = new Cookie(username+"-his","");
				his.setMaxAge(1800);
			}
		}
		else{
			his = new Cookie(username+"-his","");
			his.setMaxAge(1800);
		}

		String hisval = his.getValue();
		List<Map> returnHis = new ArrayList<>();
		if (hisval.equals("") || hisval == null)
			hisval = product.getName()+"_"+product.getId();
		else {
			// Replace existed history
			String newRecord = product.getName()+"_"+product.getId();
			if (hisval.contains(newRecord)){
				if (hisval.contains(",")){
					if (hisval.contains(newRecord+","))
						hisval = hisval.replace(newRecord+",","");
					else if (hisval.contains(","+newRecord))
						hisval = hisval.replace(","+newRecord,"");
					else
						throw new RuntimeException("Record replacement of comma is wrong");
				}
				else{
					Map<String,String> tmpMap = new HashMap<>();
					tmpMap.put("name",product.getName());
					tmpMap.put("id",String.valueOf(product.getId()));
					returnHis.add(tmpMap);
					return returnHis;
				}
			}

			String[] hisArray = hisval.split(",");
			if (hisArray.length<10)
				hisval = newRecord+","+hisval;
			else{
				hisval = hisval.substring(hisval.indexOf(",")+1,hisval.length());
				hisval = newRecord+","+hisval;
			}
		}
		his.setValue(hisval);
		response.addCookie(his);
		String[] hisArray;
		if (hisval.contains(","))
			hisArray = hisval.split(",");
		else{
			hisArray = new String[1];
			hisArray[0] = hisval;
		}
		for(String record : hisArray){
			String[] del = record.split("_");
			Map<String,String> tmpMap = new HashMap<>();
			tmpMap.put("name",del[0]);
			tmpMap.put("id",del[1]);
			returnHis.add(tmpMap);
		}
		return returnHis;
	}

}
