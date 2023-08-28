/**
 * 
 */
package com.wibmo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "admin")
public class Admin {
	
	@Id
	@Column(name = "admin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminId;		// admin_id (PK)
	
	@Column(name = "user_id")
	private Integer userId;			// user_id (FK)
	
//	@OneToOne(cascade = CascadeType.ALL, optional = false)
//    @JoinColumn(name = "admin_id")
//    @MapsId
//	private User user;
	
	@Column(name = "admin_email")
	@NotNull
	private String adminEmail;		// admin_email
	
	@Column(name = "admin_name")
	@NotNull
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
	
}
