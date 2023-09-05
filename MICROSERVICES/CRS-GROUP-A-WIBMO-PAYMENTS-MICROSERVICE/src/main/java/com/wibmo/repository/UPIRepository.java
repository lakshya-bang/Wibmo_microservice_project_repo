package com.wibmo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.UPI;

@Repository
public interface UPIRepository extends CrudRepository<UPI, Integer>{
	/**
	 * This method is to find upi details.
	 * @param upiId upi id of user for payment
	 * @return Optional Upi object or null
	 */
	Optional<UPI> findByUpiId(String upiId);
	
}
