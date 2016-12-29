package com.sjw.ShiroTest.Msg;

import com.sjw.ShiroTest.Pojo.MsgWrapperPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import com.sjw.ShiroTest.Service.ProductService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by Watson on 11/28/2016.
 */
public class ProductMsgListener {
    @Autowired
    ProductService productService;

    public void handleMessage(Serializable message){
        //TODO unwrapper the MsgWrapper and deal certain operations. User handleMessage as Facade
        if(message instanceof MsgWrapperPojo){
            MsgWrapperPojo unWrapper = (MsgWrapperPojo) message;
            String msg = unWrapper.getMsg();
            if (unWrapper.getObj() instanceof ProductPojo){
                ProductPojo pdct = (ProductPojo) unWrapper.getObj();
                if (msg.equals("history-update")){
                    String username = unWrapper.getUsername();
                    productService.updateReadHistory(pdct,username);
                }
            }
            else
                throw new RuntimeException("Object type isn't product type");

        }
        else
            throw new RuntimeException("Message type is wrong");
    }
}
