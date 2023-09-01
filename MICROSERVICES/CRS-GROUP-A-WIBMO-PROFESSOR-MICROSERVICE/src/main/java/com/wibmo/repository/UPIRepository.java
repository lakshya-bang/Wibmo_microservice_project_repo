package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.UPI;

@Repository
public interface UPIRepository extends CrudRepository<UPI, Integer>{
	
	Optional<UPI> findByUpiId(String upiId);
	
}
