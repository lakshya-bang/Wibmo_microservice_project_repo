/**
 * 
 */
package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.entity.Notification;
import com.wibmo.entity.Student;
import com.wibmo.entity.User;
import com.wibmo.enums.NotificationType;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.repository.AdminRepository;
import com.wibmo.repository.CourseRegistrationRepository;
import com.wibmo.repository.StudentRepository;
import com.wibmo.repository.UserRepository;

/**
 * 
 */
@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRegistrationRepository courseRegistrationRepository;
	
	
	@Override
	public ResponseEntity<String> SendApproveOrRejectNotification(String jwt, 
			Set<Integer> courseRegistrationIds, 
			String notificationMessage) {
		
		 HttpHeaders headers = new HttpHeaders();
		 headers.setBearerAuth(jwt);
		 
		 List<Optional<User>> users = courseRegistrationIds
				 .stream()
				 .map(courseRegistrationId -> {
		                Integer studentId = courseRegistrationRepository
		                		.findByRegistrationId(courseRegistrationId)
		                        .get().getStudentId();
		                Integer userId = studentRepository.findByStudentId(studentId)
		                        .get().getUserId();
		                return userRepository.findByUserId(userId);
		            }).collect(Collectors.toList());
		 
		 
		 List<Notification> notificationList = new ArrayList<>();
		 for(Optional<User> user: users) {
			 if(user.isPresent()) {
				 Notification notification = new Notification();
				 notification.setNotificationUserId(user.get().getUserId());
				 notification.setNotificationMessage(notificationMessage);
				 notification.setNotificationType(NotificationType.REGISTRATION);
				 notificationList.add(notification);
			 }
		 }
		 HttpEntity<List<Notification>> request = new HttpEntity<>(notificationList, headers);
		 return new RestTemplate().exchange(
		            "http://localhost:8086/api/notification/send-all-notifications/REGISTRATION",
		            HttpMethod.POST, request, String.class);
	}

}
