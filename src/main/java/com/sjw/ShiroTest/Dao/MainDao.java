package com.sjw.ShiroTest.Dao;

import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/11/2017.
 */
@MapperScan
public interface MainDao {
    public List<Map> getMainMenu();
}
