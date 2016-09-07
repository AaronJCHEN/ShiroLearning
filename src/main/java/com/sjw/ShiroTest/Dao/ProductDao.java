package com.sjw.ShiroTest.Dao;

import java.util.List;

import com.sjw.ShiroTest.Pojo.ProductPojo;

public interface ProductDao {
	public List<ProductPojo> getRecommendedProductsDao();
	public ProductPojo getProductDetailDao(int id);
	public void updateBrowseNumDao(int id);
}
