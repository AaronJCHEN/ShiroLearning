package com.sjw.ShiroTest.Utils;

public enum RoleType {
	USER("USER"),MANAGER("MANAGER"),ADMIN("ADMIN");
	
	private String role_name;
	
	private RoleType(String role_name){
		this.role_name = role_name;
	}
	
	public String getRole(){
		return this.role_name;
	}
	
}
