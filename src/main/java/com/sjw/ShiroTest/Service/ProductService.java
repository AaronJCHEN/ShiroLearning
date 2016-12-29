package com.sjw.ShiroTest.Service;

import java.util.List;

import com.sjw.ShiroTest.Pojo.ProductPojo;

import javax.servlet.http.HttpSession;

public interface ProductService {
	public List<ProductPojo> getRecommendedProductsService();
	public ProductPojo getProductDetailService(int id);
	public void updateBrowseNumService(int id);
	public List<ProductPojo> updateReadHistory(ProductPojo product, String username);
}
