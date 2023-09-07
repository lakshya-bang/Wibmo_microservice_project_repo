/**
 * 
 */
package com.wibmo.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Notification;

/**
 * 
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer>{

	@Query(value = "SELECT notification from Notification notification WHERE notification.notificationUserId = :forUserId AND notification.notificationStatus = 'PENDING'")
	ArrayList<Notification> findAllByNotificationUserId(Integer forUserId);

	@Query(value = "SELECT notification from Notification notification WHERE notification.notificationUserId = :userId")
	ArrayList<Notification> fetchAllByUserId(Integer userId);

}
