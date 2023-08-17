package com.wibmo.bean;

/**
 */
public class Admin {

	private Integer adminId;		// admin_id
	private String adminName;		// admin_name
	
	public Admin() {}
	
	public Admin(
			Integer adminId, 
			String adminName,
			String adminEmail) {
		this.adminId = adminId;
		this.adminName = adminName;
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
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName + "]";
	}
}
