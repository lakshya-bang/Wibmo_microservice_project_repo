/**
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.UPIDTO;
import com.wibmo.repository.UPIRepository;

/**
 * 
 */
@Service
public class UPIServiceImpl implements UPIService {

	@Autowired
	private UPIRepository upiRepository;
	
	@Override
	public Boolean verify(UPIDTO upiDTO) {
		return upiRepository
				.findByUpiId(upiDTO.getUpiId())
				.isPresent();
	}

	
}
