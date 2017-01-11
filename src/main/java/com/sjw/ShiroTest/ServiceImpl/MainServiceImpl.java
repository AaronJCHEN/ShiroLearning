package com.sjw.ShiroTest.ServiceImpl;

import com.sjw.ShiroTest.Dao.MainDao;
import com.sjw.ShiroTest.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/11/2017.
 */
@Service
public class MainServiceImpl implements MainService {
    @Autowired
    MainDao mainDao;

    @Override
    public List<Map> getMenuService() {
        return mainDao.getMainMenuDao();
    }
}
