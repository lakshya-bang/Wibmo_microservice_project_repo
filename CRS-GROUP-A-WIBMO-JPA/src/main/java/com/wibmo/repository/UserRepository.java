/**
 * 
 */
package com.wibmo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Course;
import com.wibmo.entity.User;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
@Repository
@Component
public interface UserRepository extends CrudRepository<User, Integer>{

	List<User> findAllByRegistrationStatus(RegistrationStatus pending);

	boolean update(String string, int userId);

	Integer findUserIdByEmail(String email);

	Boolean updateRegistrationStatusAsByIdIn(RegistrationStatus registrationStatus, Set<Integer> userIds);

}
