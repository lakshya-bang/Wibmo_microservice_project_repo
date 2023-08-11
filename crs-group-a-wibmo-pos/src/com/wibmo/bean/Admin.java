package com.wibmo.bean;

import java.util.Set;

import com.wibmo.enums.AdminRole;

public class Admin {

	private String name;
	private Long phoneNum;
	private String email;
	private Set<AdminRole> roles;
	
	private Long id;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phoneNum
	 */
	public Long getPhoneNum() {
		return phoneNum;
	}
	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(Long phoneNum) {
		this.phoneNum = phoneNum;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the roles
	 */
	public Set<AdminRole> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<AdminRole> roles) {
		this.roles = roles;
	}
	
	/**
	 * Adds the given roles into the current roles
	 * @param roles
	 */
	public void addRoles(Set<AdminRole> roles) {
		this.roles.addAll(roles);
	}
	
}
