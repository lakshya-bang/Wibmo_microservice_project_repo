package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.wibmo.entity.Payment;

/**
 * 
 */
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

	Optional<Payment> findByCourseRegistrationId(Integer courseRegistrationId);
	
	List<Payment> findAll();
	
	List<Payment> findAllByCourseRegistrationIdIn(Collection<Integer> courseRegistrationIds);
}
