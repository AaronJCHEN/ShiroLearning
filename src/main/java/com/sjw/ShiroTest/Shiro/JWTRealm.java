package com.sjw.ShiroTest.Shiro;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sjw.ShiroTest.Pojo.JWTTokenPojo;
import com.sjw.ShiroTest.Service.AuthService;
import com.sjw.ShiroTest.Utils.JWTUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

public class JWTRealm extends AuthorizingRealm {

    @Autowired
    AuthService authService;

    @Autowired
    JWTUtils jwtUtils;

    private Logger logger = LoggerFactory.getLogger(JWTRealm.class);

    private boolean permissionsLookupEnabled;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTTokenPojo;
    }

    //Give authority to login
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.debug("In the realm authentication method");

        JWTTokenPojo token = (JWTTokenPojo) authenticationToken;
        String username = token.getUsername();
        SimpleAuthenticationInfo info = null;
        try {
            if (jwtUtils.verify(token.getToken(),username)){
                info = new SimpleAuthenticationInfo(token.getPrincipal(),
                        token.getCredentials(),
                        "JWTRealm");
            }
            else
                logger.error("Authentication verify error");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JWTVerificationException ex) {

        }
        return info;
    }

    //Give permission to deal auth_required method
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.debug("In the realm authorization method");

        if (principalCollection == null){
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principalCollection);

        Set<String> roleNames = null;
        Set<String> permissions = null;
        try{
            roleNames = getRoleNamesForUser(username);
            if (permissionsLookupEnabled) {
                permissions = getPermissions(username, roleNames);
            }
        }
        catch (Exception e){
            final String message = "There was a SQL error while authorizing user [" + username + "]";
            if (logger.isErrorEnabled()) {
                logger.error(message, e);
            }

            // Rethrow any SQL errors as an authorization exception
            throw new AuthorizationException(message, e);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    protected Set<String> getRoleNamesForUser(String username) throws SQLException {
        Set<String> roleNames = new LinkedHashSet<String>();
        List<String> role_list = authService.getRoleListService(username);
        if(!role_list.isEmpty()){
            Iterator<String> i = role_list.iterator();
            while(i.hasNext()){
                String roleName = i.next();
                if (roleName != null) {
                    roleNames.add(roleName);
                } else {
                    if (logger.isWarnEnabled()) {
                        logger.warn("Null role name found while retrieving role names for user [" + username + "]");
                    }
                }
            }
        }
        return roleNames;
    }

    protected Set<String> getPermissions(String username, Collection<String> roleNames)
            throws SQLException {
        Set<String> permissions = new LinkedHashSet<String>();
        for (String roleName : roleNames) {
            List<String> permisson_list = authService.getPermissionListService(roleName);
            Iterator<String> i = permisson_list.iterator();
            while(i.hasNext()){
                permissions.add(i.next());
            }
        }
        return permissions;
    }

    public boolean isPermissionsLookupEnabled() {
        return permissionsLookupEnabled;
    }

    public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
        this.permissionsLookupEnabled = permissionsLookupEnabled;
    }
}