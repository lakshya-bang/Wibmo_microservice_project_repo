/**
 * 
 */
package com.wibmo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.NetBankingDTO;
import com.wibmo.entity.NetBanking;
import com.wibmo.repository.NetBankingRepository;

/**
 * 
 */
@Service
public class NetBankingServiceImpl implements NetBankingService {

	@Autowired
	private NetBankingRepository netBankingRepository;
	
	@Override
	public Boolean verify(NetBankingDTO netBankingDTO) {
		Optional<NetBanking> netBankingOptional = 
				netBankingRepository.findByUserNameAndPassword(
						netBankingDTO.getUserName(), 
						netBankingDTO.getPassword());
		return netBankingOptional.isPresent();
	}

}
