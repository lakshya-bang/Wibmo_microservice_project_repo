/**
 * 
 */
package com.wibmo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "upi")
public class UPI {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "upi_id")
	private String upiId;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the upiId
	 */
	public String getUpiId() {
		return upiId;
	}

	/**
	 * @param upiId the upiId to set
	 */
	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public UPI(String upiId) {
		this.upiId = upiId;
	}
	
	public UPI() {}
	
}
