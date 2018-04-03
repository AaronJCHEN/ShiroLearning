package com.sjw.ShiroTest.Dao;

import com.sjw.ShiroTest.Pojo.OrderPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface OrderDao {
    void addOrder(OrderPojo orderPojo);
    void addOrderDetail(OrderPojo orderPojo);
}
