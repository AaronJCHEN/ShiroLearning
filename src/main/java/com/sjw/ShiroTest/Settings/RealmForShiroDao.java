package com.sjw.ShiroTest.Settings;

import java.util.List;

public interface RealmForShiroDao {

	List<String> getRoleList(String username);

	List<String> getPermissionList(String roleName);

	List<String> getPassword(String username);

}