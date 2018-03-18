package com.sjw.ShiroTest.Dao;

import java.util.List;
import java.util.Map;

import com.sjw.ShiroTest.Pojo.BrowsePojo;
import com.sjw.ShiroTest.Pojo.CommentPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ProductDao {
	List<ProductPojo> getRecommendedProducts();
	ProductPojo getProductDetail(int id);
	ProductPojo getProductDetailByName(String name);
	void updateRemainCount(ProductPojo product);
	BrowsePojo getBrowseDetail(BrowsePojo browse);
	void createBrowseRecord(BrowsePojo browse);
	int getBrowseTimes(int productId);
	void updateBrowseTimes(BrowsePojo browse);
	void updateRemainCount(int productId);
	void createNewProduct(ProductPojo product);
	List<CommentPojo> getCommentsByProduct(Map params);
}
