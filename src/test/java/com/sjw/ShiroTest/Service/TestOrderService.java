package com.sjw.ShiroTest.Service;

import com.sjw.ShiroTest.Pojo.OrderPojo;
import com.sjw.ShiroTest.Pojo.ResponsePojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Watson on 07/19/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestOrderService {
    @Autowired
    OrderService orderService;

    @Test
    public void testAddSaleInfoService() throws ExecutionException, InterruptedException {
        List<OrderPojo> orderPojoList = new ArrayList<>();
        for (int i=0; i<9; i++){
            OrderPojo orderPojo = new OrderPojo();
            orderPojo.setId(123456);
            orderPojoList.add(orderPojo);
        }

        Iterator<OrderPojo> it = orderPojoList.iterator();
        List<FutureTask> task = new LinkedList<>();
        ExecutorService ex = Executors.newFixedThreadPool(10);
        while (it.hasNext()){
            OrderPojo orderPojo = it.next();
            Callable<ResponsePojo> cal = new Callable<ResponsePojo>() {
                @Override
                public ResponsePojo call() throws Exception {
                    return orderService.addSaleInfoService(orderPojo);
                }
            };
            FutureTask<ResponsePojo> futureTask = new FutureTask<>(cal);
            task.add(futureTask);
            ex.submit(futureTask);
        }
        for (FutureTask<ResponsePojo> f : task){
            ResponsePojo r = f.get();
            System.out.println(r.getMsg());
        }

    }
}
