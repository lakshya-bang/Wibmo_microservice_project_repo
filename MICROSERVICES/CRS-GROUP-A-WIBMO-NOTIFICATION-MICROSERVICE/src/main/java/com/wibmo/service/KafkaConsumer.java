package com.wibmo.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.wibmo.entity.Notification;
import com.wibmo.repository.NotificationRepository;

@Service
@Component
public class KafkaConsumer {
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	NotificationServiceImpl notificationService;
	
	@KafkaListener(topics="REGISTRATION", groupId = "students", containerFactory="notificationListener")
	public void consumeRegistration(Notification notification) {
		notificationService.createNotification(notification.getNotificationUserId(), notification.getNotificationMessage(), notification.getNotificationType(), notification.getNotificationStatus());
	}
	
	@KafkaListener(topics="PAYMENT", groupId = "students", containerFactory="notificationListener")
	public void consumePayment(Notification notification) {
		notificationService.createNotification(notification.getNotificationUserId(), notification.getNotificationMessage(), notification.getNotificationType(), notification.getNotificationStatus());
	}
	
}
