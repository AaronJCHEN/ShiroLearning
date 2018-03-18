package com.sjw.ShiroTest.Service;

import com.sjw.ShiroTest.Pojo.OrderPojo;
import com.sjw.ShiroTest.Pojo.ResponsePojo;


public interface OrderService {
    ResponsePojo addOrderInfoService(OrderPojo orderPojo);
}
