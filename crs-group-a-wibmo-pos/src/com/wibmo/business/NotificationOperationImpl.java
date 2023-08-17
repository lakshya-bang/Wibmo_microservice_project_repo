/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;
import com.wibmo.bean.*;
import com.wibmo.dao.NotificationDAO;
import com.wibmo.dao.NotificationDAOImpl;
/**
 * 
 */
public class NotificationOperationImpl implements NotificationOperation{

	NotificationDAO notificationDAO = NotificationDAOImpl.getInstance();

	@Override
	public void createNotification(Integer forUserId, String message) {
		Notification notification = new Notification();
		notification.setNotificationUserId(forUserId);
		notification.setNotificationMessage(message);
		notificationDAO.create(notification);
	}

	@Override
	public ArrayList<Notification> retrieveMessages(Integer forUserId) {
		ArrayList<Notification> notifications;
		notifications = notificationDAO.fetchByUserId(forUserId);
		updateStatus(notifications);
		return notifications;
	}

	@Override
	public void updateStatus(ArrayList<Notification> notifications) {
		notificationDAO.updateStatus(notifications);
	}

}
