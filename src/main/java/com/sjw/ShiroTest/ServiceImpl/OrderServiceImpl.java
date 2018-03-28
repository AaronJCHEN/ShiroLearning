package com.sjw.ShiroTest.ServiceImpl;

import com.sjw.ShiroTest.Dao.OrderDao;
import com.sjw.ShiroTest.Pojo.OrderPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Pojo.ResponsePojo;
import com.sjw.ShiroTest.Service.OrderService;
import com.sjw.ShiroTest.Service.ProductService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponsePojo addOrderInfoService(OrderPojo orderPojo) {
        // Verify account
        /*double sum = 0;
        for(ProductPojo p : orderPojo.getOrderList())
            sum = sum + p.getPrice() * p.getProductAmount();*/

        if (true){
            Session session = SecurityUtils.getSubject().getSession();
            //orderPojo.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
            //orderPojo.setUserId(1);
            orderDao.addOrderInfo(orderPojo);
            // remove remains
            for (ProductPojo p : orderPojo.getOrderList())
                productService.updateRemainCountService(p);

            return new ResponsePojo(HttpStatus.OK.value()
                    ,"Success","Make order success");

        }
        else
            return new ResponsePojo(HttpStatus.BAD_REQUEST.value()
                    ,"Bad request","Sun account is wrong");
    }
}
