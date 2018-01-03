package com.sjw.ShiroTest.Shiro;

import java.sql.SQLException;
import java.util.*;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sjw.ShiroTest.Service.AuthService;

/**
 * Extends from the JdbcRealm to use the mybatis instead of pure jdbc function.
 * Pure JDBC function has conflicts on logging when using Druid
 * Also can change the authorizing part by access_level if needed
 * version 1.0
 */

public class RealmForShiro extends JdbcRealm {
	
	@Autowired
	AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(RealmForShiro.class);

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        SimpleAuthenticationInfo info = null;
        try{
        	String password = null;
            String salt = null;
            switch (saltStyle) {
            case NO_SALT:
                password = this.getPasswordForUser(username)[0];
                break;
            case CRYPT:
                // TODO: separate password and hash from getPasswordForUser[0]
                throw new ConfigurationException("Not implemented yet");
                //break;
            case COLUMN:
                String[] queryResults = getPasswordForUser(username);
                password = queryResults[0];
                salt = queryResults[1];
                break;
            case EXTERNAL:
                password = getPasswordForUser(username)[0];
                salt = getSaltForUser(username);
            }

            if (password == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }

            info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
            if (salt != null) {
                info.setCredentialsSalt(ByteSource.Util.bytes(salt));
            }
        }
        catch(Exception e){
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }

            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        }

        return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null){
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

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
            if (log.isErrorEnabled()) {
                log.error(message, e);
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
                    if (log.isWarnEnabled()) {
                        log.warn("Null role name found while retrieving role names for user [" + username + "]");
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
	
	private String[] getPasswordForUser(String username){
		List<String> rl = authService.getPasswordService(username);
		String[] result = new String[rl.size()];
        int times = 0;
        Iterator<String> i = rl.iterator();
        while (i.hasNext()){
            result[times] = i.next().toString();
            times++;
        }
        return result;
	}
	
}
