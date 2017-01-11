package com.sjw.ShiroTest.DaoImpl;

import com.sjw.ShiroTest.Dao.MainDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/11/2017.
 */
@Repository
public class MainDaoImpl implements MainDao {
    @Autowired
    SqlSession sqlSession;

    @Override
    public List<Map> getMainMenuDao() {
        return sqlSession.selectList("getMainMenu");
    }
}
