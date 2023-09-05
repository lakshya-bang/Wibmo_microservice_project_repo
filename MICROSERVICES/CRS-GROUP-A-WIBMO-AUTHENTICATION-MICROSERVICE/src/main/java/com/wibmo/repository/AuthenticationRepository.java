package com.wibmo.repository;


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
	 * Fetches User on the basis of userEmail and password.
	 * @param userEmail
	 * @param userPassword
	 * @return User
	 */
	@Query(value = "SELECT * from auth_creds WHERE user_email = :userEmail AND user_password = :userPassword", nativeQuery =true)
	User findByEmailAndPassword(@Param("userEmail")String userEmail, @Param("userPassword")String userPassword);
	/**
	 * Fetches user on the basis of userEmail.
	 * @param userEmail
	 * @return User
	 */
	@Query(value = "SELECT user from User user WHERE user.userEmail = :userEmail")
	User findByuserEmail(String userEmail);
	/**
	 * Checks if the userEmail exists in the DB.
	 * @param userEmail
	 * @return
	 */
	Boolean existsByUserEmail(String userEmail);
}
