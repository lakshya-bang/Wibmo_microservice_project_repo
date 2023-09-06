/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.entity.Notification;
import com.wibmo.enums.NotificationStatus;
import com.wibmo.enums.NotificationType;
import com.wibmo.exception.UnexpectedTopicException;
import com.wibmo.repository.NotificationRepository;

/**
 * 
 */
@Service
@Component
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	NotificationRepository notificationRepository;
	

	@Override
	public void createNotification(Integer forUserId, String message, NotificationType notificationType, NotificationStatus notificationStatus) {
		Notification notification = new Notification();
		notification.setNotificationUserId(forUserId);
		notification.setNotificationMessage(message);
		notification.setNotificationStatus(notificationStatus);
		notification.setNotificationType(notificationType);
		notificationRepository.save(notification);
	}

	@Override
	public ArrayList<Notification> retrieveMessages(Integer forUserId) {
		ArrayList<Notification> notifications;
		notifications = notificationRepository.findAllByNotificationUserId(forUserId);
		updateStatus(notifications);
		return notifications;
	}

	private void updateStatus(ArrayList<Notification> notifications) {
		for(Notification notification : notifications) {
			notification.setNotificationStatus(NotificationStatus.ACKNOWLEDGED);

		}
		notificationRepository.saveAll(notifications);
	}

	@Override
	public ArrayList<Notification> fetchAllNotificationsByUserId(Integer userId){
		return notificationRepository.fetchAllByUserId(userId);
	}
	

}
