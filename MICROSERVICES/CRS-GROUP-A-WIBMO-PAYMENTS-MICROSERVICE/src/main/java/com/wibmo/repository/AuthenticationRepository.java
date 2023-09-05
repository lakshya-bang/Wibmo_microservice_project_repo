/**
 * 
 */
package com.wibmo.repository;

/**
 * 
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.User;

@Repository
@Component
public interface AuthenticationRepository extends CrudRepository<User,Integer>{

	/**
	 * This method is to get User by Email and password for verification
	 * @param userEmail email of the user to pass in query
	 * @param userPassword password of the user to pass in query
	 * @return User object
	 */
	@Query(value = "SELECT * from auth_creds WHERE user_email = :userEmail AND user_password = :userPassword", nativeQuery =true)
	User findByEmailAndPassword(@Param("userEmail")String userEmail, @Param("userPassword")String userPassword);
	
	/**
	 * This method is to find user by email
	 * @param userEmail email of the user to pass in query
	 * @return User object
	 */
	@Query(value = "SELECT user from User user WHERE user.userEmail = :userEmail")
	User findByuserEmail(String userEmail);
	
	/**
	 * This method is to check existence of user by email
	 * @param userEmail email of the user to pass in query
	 * @return User object
	 */
	Boolean existsByUserEmail(String userEmail);
}