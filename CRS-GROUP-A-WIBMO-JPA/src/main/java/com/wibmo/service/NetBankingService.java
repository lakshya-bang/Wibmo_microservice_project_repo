/**
 * 
 */
package com.wibmo.service;

import com.wibmo.dto.NetBankingDTO;

/**
 * 
 */
public interface NetBankingService {

	/**
	 * 
	 * @param netBankingDTO
	 * @return
	 */
	public Boolean verify(NetBankingDTO netBankingDTO);
}
