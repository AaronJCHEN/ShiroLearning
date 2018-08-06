package com.sjw.ShiroTest.Msg;

import com.alibaba.fastjson.JSONObject;
import com.sjw.ShiroTest.Dao.OrderDao;
import com.sjw.ShiroTest.Pojo.OrderPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Created by Watson on 07/20/2018
 */
@Component
public class SalesListener {
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductService productService;

    @RabbitListener(queues = "salesQueue", containerFactory = "salesListenerFactory")
    public void salesDB2Ops(@Payload OrderPojo orderPojo){
        orderDao.addOrder(orderPojo);
        orderDao.addOrderDetail(orderPojo);
        // remove remains
        for (ProductPojo p : orderPojo.getProductList())
            productService.updateRemainCountService(p);
    }
}
