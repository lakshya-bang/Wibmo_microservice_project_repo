/**
 * 
 */
package com.wibmo.business;

import java.util.Map;
import java.util.Set;

import com.wibmo.bean.User;

/**
 * 
 */
public interface UserOperation {

	/**
	 * 
	 * @param userIds
	 * @return
	 */
	public Map<Integer, User> getUserIdToUserMap(Set<Integer> userIds);
}
