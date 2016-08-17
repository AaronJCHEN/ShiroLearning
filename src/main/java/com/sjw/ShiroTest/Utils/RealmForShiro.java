package com.sjw.ShiroTest.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Extends from the JdbcRealm to use the mybatis instead of pure jdbc function.
 * Pure JDBC function has conflicts on logging when using Druid
 * Also can change the authorizing part by access_level if needed
 * version 1.0
 */
public class RealmForShiro extends JdbcRealm {
	@Autowired
	SqlSession sqlSession;

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
        }
        catch(AuthenticationException e){
        	e.printStackTrace();
        }
        
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Set<String> getRoleNamesForUser(String username) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Set<String> getPermissions(String username, Collection<String> roleNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String[] getPasswordForUser(String username){
		List<String> rl = this.sqlSession.selectList("getPswd4User",username);
		return (String[]) rl.stream().toArray();
	}

}
