package com.sjw.ShiroTest.Dao;

import java.util.List;

import com.sjw.ShiroTest.Pojo.BrowsePojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ProductDao {
	public List<ProductPojo> getRecommendedProducts();
	public ProductPojo getProductDetail(int id);
	public BrowsePojo getBrowseDetail(BrowsePojo browse);
	public void createBrowseRecord(BrowsePojo browse);
	public void updateBrowseTimes(BrowsePojo browse);
}
