package com.sjw.ShiroTest.Pojo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.authc.AuthenticationToken;

public class JWTTokenPojo implements AuthenticationToken {

    private String token;

    private String username;

    public JWTTokenPojo(){

    }

    public JWTTokenPojo(String token) {
        this.token = token;

        DecodedJWT jwt = JWT.decode(token);
        this.username = jwt.getClaim("username").asString();
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
