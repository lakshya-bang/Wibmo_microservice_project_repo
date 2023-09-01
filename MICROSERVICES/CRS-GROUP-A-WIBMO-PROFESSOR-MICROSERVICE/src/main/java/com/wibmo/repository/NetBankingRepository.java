package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.NetBanking;

@Repository
public interface NetBankingRepository extends CrudRepository<NetBanking, Integer>{

	Optional<NetBanking> findByUserNameAndPassword(String userName, String password);
	
}
