/**
 * 
 */
package com.wibmo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.UserNotApprovedException;
import com.wibmo.repository.AuthenticationRepository;

/**
 * 
 */
@Component
public class LoginUtil {
	@Autowired
	AuthenticationRepository authenticationRepository;
	
	/**
	 * Checks if the user is approved.
	 * @param userEmail
	 * @return
	 * @throws UserNotApprovedException
	 */
	public Boolean isApproved(String userEmail) throws UserNotApprovedException{
		if(authenticationRepository.findByuserEmail(userEmail).getRegistrationStatus().equals(RegistrationStatus.APPROVED)) {
			return true;
		}
		throw new UserNotApprovedException(userEmail);
	}
}
