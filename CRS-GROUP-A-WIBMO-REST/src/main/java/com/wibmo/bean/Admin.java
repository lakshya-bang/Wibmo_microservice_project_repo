package com.wibmo.bean;

/**
 * 
 */
public class Admin {

	private Integer adminId;		// admin_id
	private String adminEmail;		// admin_email
	private String adminName;		// admin_name
	
	public Admin() {}
	
	public Admin(
			Integer adminId, 
			String adminEmail,
			String adminName) {
		this.adminId = adminId;
		this.adminEmail = adminEmail;
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
	 * @return the adminEmail
	 */
	public String getAdminEmail() {
		return adminEmail;
	}

	/**
	 * @param adminEmail the adminEmail to set
	 */
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
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
		return "Admin [adminId=" + adminId + ", adminEmail=" + adminEmail + ", adminName=" + adminName + "]";
	}

}
