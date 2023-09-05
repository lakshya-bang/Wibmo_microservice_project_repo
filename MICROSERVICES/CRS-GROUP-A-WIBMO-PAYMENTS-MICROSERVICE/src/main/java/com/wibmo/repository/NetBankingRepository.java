package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.NetBanking;

@Repository
public interface NetBankingRepository extends CrudRepository<NetBanking, Integer>{

	/**
	 * This method is to find net banking details
	 * @param userName name of the user
	 * @param password password of the user
	 * @return Optional NetBanking Object or null
	 */
	Optional<NetBanking> findByUserNameAndPassword(String userName, String password);
	
}
