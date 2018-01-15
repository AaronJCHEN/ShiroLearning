package com.sjw.ShiroTest.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SessionAop {
    private Logger logger = LoggerFactory.getLogger(SessionAop.class);
    private final String POINT_CUT = "execution(* org.springframework.session.data.redis.RedisOperationsSessionRepository.save(..))";

    @Before(POINT_CUT)
    public void sessionSaveAop(JoinPoint joinPoint) {
        logger.info("Before the RedisOperationsSessionRepository save method");
    }
}
