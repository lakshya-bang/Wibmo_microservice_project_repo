/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import com.wibmo.entity.Notification;
import com.wibmo.enums.NotificationStatus;
import com.wibmo.enums.NotificationType;


/**
 * @author lakshya.bang
 */
public interface NotificationService {
	
	/**
	 * Fetches all the entries in the Notification table corresponding to the give userId
	 * @param forUserId (Integer)
	 * @return ArrayList<Notification>
	 */
	public ArrayList<Notification> retrieveMessages(Integer forUserId);
	

	/**
	 * 
	 * @param userId
	 * @return
	 */

	public ArrayList<Notification> fetchAllNotificationsByUserId(Integer userId);
	
	/**
	 * 
	 * @param forUserId
	 * @param message
	 * @param notificationType
	 * @param notificationStatus
	 */
	void createNotification(Integer forUserId, String message, NotificationType notificationType,
			NotificationStatus notificationStatus);
	
}
