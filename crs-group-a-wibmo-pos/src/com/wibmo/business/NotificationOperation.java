/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;

import com.wibmo.bean.Notification;

/**
 * @author lakshya.bang
 */
public interface NotificationOperation {
	
	/**
	 * Creates a new entry in the Notification table
	 * @param forUserId (Integer)
	 * @param message (String)
	 */
	public void createNotification(Integer forUserId, String message);
	/**
	 * Fetches all the entries in the Notification table corresponding to the give userId
	 * @param forUserId (Integer)
	 * @return ArrayList<Notification>
	 */
	public ArrayList<Notification> retrieveMessages(Integer forUserId);
	/**
	 * Updates the status of the Notification in the Table
	 * @param notifications
	 */
	public void updateStatus(ArrayList<Notification> notifications);

	/**
	 * 
	 * @param userId
	 * @return
	 */

	public ArrayList<Notification> fetchAllNotificationsByUserId(Integer userId);
}
