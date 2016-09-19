package com.sjw.ShiroTest.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

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

}
