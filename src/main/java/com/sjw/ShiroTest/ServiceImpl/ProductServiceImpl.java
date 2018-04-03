package com.sjw.ShiroTest.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sjw.ShiroTest.Msg.RedisMsgSender;
import com.sjw.ShiroTest.Pojo.*;
import com.sjw.ShiroTest.Service.AuthService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.ProductDao;
import com.sjw.ShiroTest.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public List<ProductPojo> getRecommendedProductsService() {
		return productDao.getRecommendedProducts();
	}

	@Override
	public ProductPojo getProductDetailService(int id) {
		return productDao.getProductDetail(id);
	}

	@Override
	public ProductPojo getProductDetailService(String name) {
		return productDao.getProductDetailByName(name);
	}

	@Override
	public void updateRemainCountService(ProductPojo product) {
		productDao.updateRemainCount(product);
	}

	@Override
	public double getRemainCountService(ProductPojo product) {
		return productDao.getRemainCount(product);
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
		String cookieName = username.substring(0,username.indexOf("@"))
				+username.substring(username.indexOf("@")+1,username.indexOf("."));
		if (cookies != null){
			for (Cookie cookie:cookies){
				if (cookie.getName().contains(cookieName+"-his")){
					his = cookie;
					isFindCookie = true;
					break;
				}
				else
					continue;
			}
			if (!isFindCookie){
				his = new Cookie(cookieName+"-his","");
				his.setPath("/ShiroTest");
				his.setMaxAge(1800);
			}
		}
		else{
			his = new Cookie(cookieName+"-his","");
			his.setPath("/ShiroTest");
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
				if (hisval.contains("#")){
					if (hisval.contains(newRecord+"#"))
						hisval = hisval.replace(newRecord+"#","");
					else if (hisval.contains("#"+newRecord))
						hisval = hisval.replace("#"+newRecord,"");
					else
						throw new RuntimeException("Record replacement of # is wrong");
				}
				else{
					Map<String,String> tmpMap = new HashMap<>();
					tmpMap.put("name",product.getName());
					tmpMap.put("id",String.valueOf(product.getId()));
					returnHis.add(tmpMap);
					return returnHis;
				}
			}

			String[] hisArray = hisval.split("#");
			if (hisArray.length<10)
				hisval = newRecord+"#"+hisval;
			else{
				hisval = hisval.substring(hisval.indexOf("#")+1,hisval.length());
				hisval = newRecord+"#"+hisval;
			}
		}
		his.setValue(hisval);
		response.addCookie(his);
		String[] hisArray;
		if (hisval.contains("#"))
			hisArray = hisval.split("#");
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

	@Override
	public List<CommentPojo> getCommentsByProductService(Map params) {
		return productDao.getCommentsByProduct(params);
	}

	@Override
	public void addPdctToCartService(String newInfo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		/**
		 * Using js instead of backend menthod. But keep these code to remain a template of how to use backend
		 * cookie and how to use the regex matches function.
		 */
		/*Cookie[] cookies = request.getCookies();
		String username = request.getSession().getAttribute("username").toString();
		Cookie cart = null;
		for (Cookie cookie : cookies){
			if (cookie.getName().contains(username+"-cart")){
				cart = cookie;
			}
		}
		if (cart != null){
			String info = cart.getValue();
			if(info.contains(newInfo)) {
				Pattern pattern = Pattern.compile(newInfo+"_\\d");
				String sub = info.substring(info.indexOf(newInfo),newInfo.length()+2);
				String num = sub.substring(sub.length()-1,sub.length());
				newInfo = newInfo + "_" + String.valueOf(Integer.valueOf(num)+1);
				Matcher matcher = pattern.matcher(info);
				if (matcher.find()){
					info = matcher.replaceFirst(newInfo);
					cart.setValue(info);
					cart.setMaxAge(1800);
				}
			}
			else {
				info = info + "," + newInfo+"_1";
				cart.setValue(info);
				cart.setMaxAge(1800);
			}
		}
		else {
			cart = new Cookie(username+"-cart",newInfo+"_1");
			cart.setMaxAge(1800);
		}
		response.addCookie(cart);*/
	}

}
