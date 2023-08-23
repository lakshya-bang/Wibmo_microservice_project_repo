/**
 * 
 */
package com.wibmo.utils;

import com.wibmo.bean.User;
import com.wibmo.dto.UserRegistrationDetails;

/**
 * 
 */
public class UserControllerUtils {
	public static User saveRegDetailsToUser(UserRegistrationDetails userRegistrationDetails) {
		User user = new User();
		user.setUserEmail(userRegistrationDetails.getUserName());
		user.setPassword(userRegistrationDetails.getPassword());
		user.setUserType(userRegistrationDetails.getUserType());
		return user;
	}
}
