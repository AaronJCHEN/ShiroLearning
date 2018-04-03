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

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResponsePojo addOrderInfoService(OrderPojo orderPojo) {
        // Verify account
        /*double sum = 0;
        for(ProductPojo p : orderPojo.getProductList())
            sum = sum + p.getPrice() * p.getProductAmount();*/

        if (true){
            Session session = SecurityUtils.getSubject().getSession();
            //orderPojo.setUserId(Integer.parseInt(session.getAttribute("userId").toString()));
            orderPojo.setUserId(1);

            //Get remains
            List<ProductPojo> soldOutProduct = new LinkedList<>();
            for (ProductPojo p : orderPojo.getProductList()){
                double remains = productService.getRemainCountService(p);
                if (remains-p.getProductAmount()>=0)
                    continue;
                else {
                    p.setRemains(remains);
                    soldOutProduct.add(p);
                }
            }
            if (soldOutProduct.size() != 0)
                orderPojo.removeSoldOutProduct(soldOutProduct);

            orderDao.addOrder(orderPojo);
            orderDao.addOrderDetail(orderPojo);
            // remove remains
            for (ProductPojo p : orderPojo.getProductList())
                productService.updateRemainCountService(p);

            if (soldOutProduct.size() == 0)
                return new ResponsePojo(HttpStatus.OK.value()
                    ,"Success","Make order success");
            else
                return new ResponsePojo(HttpStatus.OK.value(),
                        "Partly Success","Some of products sold out",soldOutProduct);

        }
        else
            return new ResponsePojo(HttpStatus.BAD_REQUEST.value()
                    ,"Bad request","Sun account is wrong");
    }
}
