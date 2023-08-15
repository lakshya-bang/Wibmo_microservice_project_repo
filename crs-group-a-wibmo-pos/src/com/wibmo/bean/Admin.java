package com.wibmo.bean;

/**
 * 
 * Table Name = admin
 * FOREIGN KEY(admin_id) REFERENCES user(user_id)
 */
public class Admin {

	private Integer adminId;	// admin_id
	private String role;		// role
	
	public Admin() {}
	
	public Admin(Integer adminId, String role) {
		this.adminId = adminId;
		this.role = role;
	}

	/**
	 * @return the adminId
	 */
	public Integer getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", role=" + role + "]";
	}
	
}
