package com.sjw.ShiroTest.Controller;

import com.sjw.ShiroTest.Pojo.OrderPojo;
import com.sjw.ShiroTest.Pojo.ResponsePojo;
import com.sjw.ShiroTest.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping(value="/ShiroTest/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    //Must use requestbody to receive json
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponsePojo submitOrderInfo(@RequestBody OrderPojo orderPojo){
        ResponsePojo response = orderService.addOrderInfoService(orderPojo);
        return response;
    }
}
