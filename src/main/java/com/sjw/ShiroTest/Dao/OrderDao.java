package com.sjw.ShiroTest.Dao;

import com.sjw.ShiroTest.Pojo.OrderPojo;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface OrderDao {
    void addOrderInfo(OrderPojo orderPojo);
}
