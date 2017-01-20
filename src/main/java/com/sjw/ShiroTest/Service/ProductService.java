package com.sjw.ShiroTest.Service;

import java.util.List;
import java.util.Map;

import com.sjw.ShiroTest.Pojo.BrowsePojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProductService {
	public List<ProductPojo> getRecommendedProductsService();
	public ProductPojo getProductDetailService(int id);
	public BrowsePojo getBrowseDetailService(BrowsePojo browse);
	public void createBrowseRecordService(BrowsePojo browse);
	public void updateBrowseTimesService(BrowsePojo browse);
	public List<Map> updateReadHistory(ProductPojo product, HttpServletRequest request, HttpServletResponse response);
}
