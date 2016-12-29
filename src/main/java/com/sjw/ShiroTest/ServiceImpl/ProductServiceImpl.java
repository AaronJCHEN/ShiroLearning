package com.sjw.ShiroTest.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.sjw.ShiroTest.Msg.RedisMsgSender;
import com.sjw.ShiroTest.Pojo.MsgWrapperPojo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.ProductDao;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;

	@Autowired
	CacheManager cacheManager;

	@Override
	public List<ProductPojo> getRecommendedProductsService() {
		return productDao.getRecommendedProductsDao();
	}

	@Override
	public ProductPojo getProductDetailService(int id) {
		return productDao.getProductDetailDao(id);
	}

	@Override
	public void updateBrowseNumService(int id) {
		productDao.updateBrowseNumDao(id);
	}

	@Override
	public void updateReadHistory(ProductPojo product, String username) {
		Cache readHistory = cacheManager.getCache("read-history");
		Element r = readHistory.get(username);

		if(r == null){
			List<ProductPojo> historyList = new ArrayList<>();
			historyList.add(product);
			Element thisReadList = new Element(username,historyList);
			readHistory.put(thisReadList);
		}
		else{
			Object readList = r.getObjectValue();
			if (readList instanceof List){
				List<ProductPojo> historyList = (List<ProductPojo>) readList;
				if(historyList.size()>10)
					throw new RuntimeException("The Cache size goes wrong");
				else{
					if (historyList.size() == 10)
						historyList.remove(0);

					historyList.add(product);
				}
			}
			else
				throw new RuntimeException("The Cache type goes wrong");
		}
	}

}
