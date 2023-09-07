/**
 * 
 */
package com.wibmo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wibmo.dto.NotificationRequestDTO;
import com.wibmo.entity.Notification;
import com.wibmo.entity.User;
import com.wibmo.enums.NotificationType;
import com.wibmo.repository.UserRepository;
import com.wibmo.utils.JwtTokenUtil;

/**
 * 
 */
@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	public JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<String> SendPaymentNotification(String jwt,Boolean paymentStatus) {
		// TODO Auto-generated method stub
		 HttpHeaders headers = new HttpHeaders();
		 headers.setBearerAuth(jwt);
		 Notification notification = new Notification();
		 String userEmail=jwtTokenUtil.getUsernameFromToken(jwt);
		 Optional<User> user = userRepository.findByUserEmail(userEmail);
		 if(paymentStatus==true) {
		 notification.setNotificationMessage("payment Successful");
		 }else {
			 notification.setNotificationMessage("Payment Not Successful");
		 }
		 notification.setNotificationUserId(user.get().getUserId());
		 notification.setNotificationType(NotificationType.PAYMENT);
		 HttpEntity<Notification> request = new HttpEntity<Notification>(notification);
		 return new RestTemplate().exchange("http://localhost:8086/api/notification/send-notification/PAYMENT", HttpMethod.POST, request, String.class);
	}
	
}
