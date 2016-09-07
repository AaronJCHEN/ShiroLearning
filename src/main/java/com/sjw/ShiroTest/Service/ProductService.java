package com.sjw.ShiroTest.Service;

import java.util.List;

import com.sjw.ShiroTest.Pojo.ProductPojo;

public interface ProductService {
	public List<ProductPojo> getRecommendedProductsService();
	public ProductPojo getProductDetailService(int id);
	public void updateBrowseNumService(int id);
}
