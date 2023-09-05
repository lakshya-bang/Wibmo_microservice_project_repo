/**@author mourila.vatsav
 * 
 */
package com.wibmo.dto;

/**
 * 
 */
public class NetBankingDTO {
	
	private String userName;
	private String password;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public NetBankingDTO(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
}
