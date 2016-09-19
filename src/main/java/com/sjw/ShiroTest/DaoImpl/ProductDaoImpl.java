package com.sjw.ShiroTest.DaoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.ProductDao;
import com.sjw.ShiroTest.Pojo.ProductPojo;

public class ProductDaoImpl extends SqlSessionDaoSupport implements ProductDao{

	@Override
	public List<ProductPojo> getRecommendedProductsDao() {
		return this.getSqlSession().selectList("getPdctRcmd");
	}

	@Override
	public ProductPojo getProductDetailDao(int id) {
		return this.getSqlSession().selectOne("getPdctDetail", id);
	}

	@Override
	public void updateBrowseNumDao(int id) {
		// TODO Auto-generated method stub
		
	}

}
