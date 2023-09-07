/**
 * 
 */
package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	Optional<User> findByUserId(Integer userId);
	
	Optional<User> findByUserEmail(String userEmail);
	
	List<User> findAll();
	
	List<User> findAllByUserIdIn(Collection<Integer> userIds);
	
	List<User> findAllByRegistrationStatus(RegistrationStatus registrationStatus);
	
	Boolean existsByUserEmail(String userEmail);

}
