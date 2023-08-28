package com.wibmo.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.User;

@Repository
@Component
public interface AuthenticationRepository {

	boolean authenticate(String userEmail, String userPassword);

	User getUserDetails(String userEmail);

}
