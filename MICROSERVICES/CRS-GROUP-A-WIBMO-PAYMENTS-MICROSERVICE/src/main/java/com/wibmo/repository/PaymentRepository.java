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

	/**
	 * This is a repository Method to find Payment using course registration id
	 * @param courseRegistrationId id of registered course of a student
	 * @return Optional Payment object or null
	 */
	Optional<Payment> findByCourseRegistrationId(Integer courseRegistrationId);
	
	/**
	 * This is a repository Method to find All Payment entries 
	 * @return List of Payment
	 */
	List<Payment> findAll();
	
	/**
	 * This is a repository Method to find All Payment entries given course registraton ids as list
	 * @param courseRegistrationIds id of registered course of a student
	 * @return List of Payment
	 */
	List<Payment> findAllByCourseRegistrationIdIn(Collection<Integer> courseRegistrationIds);
}
