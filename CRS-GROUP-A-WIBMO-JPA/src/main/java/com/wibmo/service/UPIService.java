/**
 * 
 */
package com.wibmo.service;

import com.wibmo.dto.UPIDTO;

/**
 * 
 */
public interface UPIService {

	/**
	 * 
	 * @param upiDTO
	 * @return
	 */
	public Boolean verify(UPIDTO upiDTO);
}
