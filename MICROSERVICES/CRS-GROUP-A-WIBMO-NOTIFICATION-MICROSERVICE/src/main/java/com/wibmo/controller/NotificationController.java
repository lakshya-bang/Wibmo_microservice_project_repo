/**
 * 
 */
package com.wibmo.controller;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.NotificationRequestDTO;
import com.wibmo.entity.Notification;
import com.wibmo.enums.NotificationStatus;
import com.wibmo.enums.NotificationType;
import com.wibmo.exception.UnexpectedTopicException;
import com.wibmo.service.NotificationServiceImpl;

import antlr.collections.List;

/**
 * 
 */
@RestController
@RequestMapping(value = "/api/notification")
public class NotificationController {
	
	
	@Autowired
	NotificationServiceImpl notificationService;
	
	 @Autowired 
	 KafkaTemplate<String, Notification> kafkaTemplate;
	 
	
	 @RequestMapping(
				produces = MediaType.APPLICATION_JSON, 
				method = RequestMethod.POST,
				value = "/send-all-notifications/{topic}")
	 public ResponseEntity sendAllNotification(@RequestBody ArrayList<Notification> notifications, @PathVariable String topic) {
		 notifications.stream().forEach(notification -> 
		 {
			 System.out.println(notification);
			 kafkaTemplate.send(topic,notification);
		 });
		 return ResponseEntity.ok("All the notifications are sent.");
	 }
	 
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.POST,
			value = "/send-notification/{topic}")
	public ResponseEntity sendNotification(@RequestBody Notification notification, @PathVariable String topic) {
		kafkaTemplate.send(topic, notification);
		return ResponseEntity.ok("Message Sent");
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON, 
			method = RequestMethod.GET,
			value = "/receive-notification/{userId}")
	public ResponseEntity receiveNotification(@PathVariable Integer userId) {
		try {
		java.util.List<Notification> notifications = notificationService.retrieveMessages(userId);
		return new ResponseEntity(notifications,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
