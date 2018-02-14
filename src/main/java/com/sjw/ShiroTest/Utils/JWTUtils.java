package com.sjw.ShiroTest.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@PropertySource("classpath:settings.properties")
public class JWTUtils {
    @Value("${token.key}")
    private String key;

    private Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    public boolean verify(String token, String username) throws UnsupportedEncodingException, JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username",username)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    public String create(String username) {
        Date date = new Date(System.currentTimeMillis()+2*60*1000);
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(key);
            String token = JWT.create()
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTokenUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }
}
