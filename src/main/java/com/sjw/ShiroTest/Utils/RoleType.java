package com.sjw.ShiroTest.Utils;

public enum RoleType {
	USER(1),MANAGER(2),ADMIN(3);
	
	private int role_id;
	
	private RoleType(int role_id){
		this.role_id = role_id;
	}
	
	public int getRole(){
		return this.role_id;
	}
	
}
