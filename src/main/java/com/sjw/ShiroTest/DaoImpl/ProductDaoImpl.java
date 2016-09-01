package com.sjw.ShiroTest.DaoImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjw.ShiroTest.Dao.ProductDao;
import com.sjw.ShiroTest.Pojo.ProductPojo;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {
	@Autowired
	SqlSession sqlSession;

	@Override
	public List<ProductPojo> getRecommendedProductsDao() {
		return this.sqlSession.selectList("getPdctRcmd");
	}

}
