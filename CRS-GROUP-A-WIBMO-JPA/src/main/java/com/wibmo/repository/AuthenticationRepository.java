package com.wibmo.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.User;

@Repository
@Component
public interface AuthenticationRepository extends CrudRepository<User,Integer>{

	@Query(value = "SELECT * from auth_creds WHERE user_email = :userEmail AND user_password = :userPassword")
	User findByEmailAndPassword(@Param("userEmail")String userEmail, @Param("userPassword")String userPassword);
	
	@Query(value = "SELECT user from auth_creds user WHERE user.user_email = :userEmail")
	User findByuserEmail(String userEmail);

}
