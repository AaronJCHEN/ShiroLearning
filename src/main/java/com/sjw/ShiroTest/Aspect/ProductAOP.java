package com.sjw.ShiroTest.Aspect;

import com.sjw.ShiroTest.Msg.RedisMsgSender;
import com.sjw.ShiroTest.Pojo.MsgWrapperPojo;
import com.sjw.ShiroTest.Pojo.ProductPojo;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Watson on 12/28/2016.
 */
@Aspect
@Component
public class ProductAOP {
    private final String POINT_CUT = "execution(* com.sjw.ShiroTest.Controller.ProductController.*(..))";
    private Logger logger = LoggerFactory.getLogger(ProductAOP.class);

    @Autowired
    RedisMsgSender redisMsgSender;
    
    @AfterReturning(returning = "rval", pointcut = POINT_CUT)
    public void afterReGetPdctDetail(Object rval){
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (rval instanceof ModelAndView){
           ModelAndView mv = (ModelAndView) rval;
           ProductPojo pdct = (ProductPojo) mv.getModel().get("pdct");
           MsgWrapperPojo<ProductPojo> msgWrapper = new MsgWrapperPojo<>("history-update"
                   ,session.getAttribute("username").toString(),pdct);
           redisMsgSender.sender("product-update",msgWrapper);
       }
       else
           throw new RuntimeException("Return type is error");
    }
}
