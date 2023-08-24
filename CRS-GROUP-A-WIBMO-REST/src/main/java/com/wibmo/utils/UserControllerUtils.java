/**
 * 
 */
package com.wibmo.utils;

import com.wibmo.dto.UserRegistrationDTO;
import com.wibmo.entity.User;

/**
 * 
 */
public class UserControllerUtils {
	public static User saveRegDetailsToUser(UserRegistrationDTO userRegistrationDetails) {
		User user = new User();
		user.setUserEmail(userRegistrationDetails.getUserName());
		user.setPassword(userRegistrationDetails.getPassword());
		user.setUserType(userRegistrationDetails.getUserType());
		return user;
	}
}
