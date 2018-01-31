package com.sjw.ShiroTest.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import sun.swing.StringUIClientPropertyKey;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtils {
    public static Boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String create(String username, String password) {
        Date date = new Date(System.currentTimeMillis()+30*60*1000);
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(password);
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
}
