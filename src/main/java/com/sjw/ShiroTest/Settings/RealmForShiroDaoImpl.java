package com.sjw.ShiroTest.Settings;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.sjw.ShiroTest.Settings.RealmForShiroDao;

@Transactional
public class RealmForShiroDaoImpl extends SqlSessionDaoSupport implements RealmForShiroDao {
	/* (non-Javadoc)
	 * @see com.sjw.ShiroTest.Settings.RealmForShiroDao#getRoleList(java.lang.String)
	 */
	@Override
	@Transactional
	public List<String> getRoleList(String username){
		return this.getSqlSession().selectList("getRoles4User", username);
	}
	
	/* (non-Javadoc)
	 * @see com.sjw.ShiroTest.Settings.RealmForShiroDao#getPermissionList(java.lang.String)
	 */
	@Override
	public List<String> getPermissionList(String roleName){
		return this.getSqlSession().selectList("getPmion4User", roleName);
	}
	
	/* (non-Javadoc)
	 * @see com.sjw.ShiroTest.Settings.RealmForShiroDao#getPassword(java.lang.String)
	 */
	@Override
	public List<String> getPassword(String username){
		return this.getSqlSession().selectList("getPswd4User",username);
	}

}
