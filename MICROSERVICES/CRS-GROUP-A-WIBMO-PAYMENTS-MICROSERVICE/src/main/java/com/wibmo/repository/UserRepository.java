/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	/**
	 * This method is to find User using user id.
	 * @param userId id of the user
	 * @return Optional user object or null
	 */
	Optional<User> findByUserId(Integer userId);
	
	/**
	 * This method is to find User using user email.
	 * @param userEmail email of the user
	 * @return Optional user object or null
	 */
	Optional<User> findByUserEmail(String userEmail);
	
	/**
	 * This method is to find All User.
	 * @return List containing users.
	 */
	List<User> findAll();
	
	/**
	 *  This method is to find All User given collection of ids.
	 * @param userIds id of the user
	 * @return List containing users.
	 */
	List<User> findAllByUserIdIn(Collection<Integer> userIds);
	
	/**
	 * This method is to find All User given the status of registration.
	 * @param registrationStatus status of registration for a course of a student
	 * @return List containing users.
	 */
	List<User> findAllByRegistrationStatus(RegistrationStatus registrationStatus);
	
	/**
	 * This method is to check is a user exists given their email.
	 * @param userEmail email of the user
	 * @return boolean true or false
	 */
	Boolean existsByUserEmail(String userEmail);
}
