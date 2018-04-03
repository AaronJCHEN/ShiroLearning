package com.sjw.ShiroTest.Service;

import java.util.List;
import java.util.Map;

import com.sjw.ShiroTest.Pojo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

public interface ProductService {
	List<ProductPojo> getRecommendedProductsService();
	ProductPojo getProductDetailService(int id);
	ProductPojo getProductDetailService(String name);
	void updateRemainCountService(ProductPojo product);
	double getRemainCountService(ProductPojo product);
	BrowsePojo getBrowseDetailService(BrowsePojo browse);
	void createBrowseRecordService(BrowsePojo browse);
	void updateBrowseTimesService(BrowsePojo browse);
	int getBrowseTimesService(int productId);
	List<Map> updateReadHistory(ProductPojo product, HttpServletRequest request, HttpServletResponse response);
	void addPdctToCartService(String newInfo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	List<CommentPojo> getCommentsByProductService(Map params);
}
