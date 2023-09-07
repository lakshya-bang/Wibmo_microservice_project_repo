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
	
	@Autowired
	private AdminService adminService;
	
	
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
		 
		 Notification notification = new Notification();
		 List<HttpEntity<Notification>> requests = new ArrayList<>();
		 for(Optional<User> user: users) {
			 notification.setNotificationUserId(user.get().getUserId());
			 notification.setNotificationMessage(notificationMessage);
			 notification.setNotificationType(NotificationType.REGISTRATION);
			 
			 HttpEntity<Notification> request = new HttpEntity<Notification>(notification);
			 requests.add(request);
		 }
		 
		 return new RestTemplate().postForEntity(
		            "http://localhost:8086/api/notification/send-all-notifications/REGISTRATION",
		            requests, String.class);
	}

	@Override
	public ResponseEntity<String> SendApproveOrRejectALLNotification(String jwt, String notificationMessage) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		
		//getting course registration DTO object to get student ID
		List<CourseRegistrationResponseDTO> courseRegistrations = adminService.getCourseRegistrationsByRegistrationStatus(
				RegistrationStatus.PENDING);
		
		List<Optional<User>> users = new ArrayList<>();
		
		for (CourseRegistrationResponseDTO cRDTO : courseRegistrations) {
	        // Get student ID to fetch users
	        Integer studentId = cRDTO.getStudentId();
	        Student student = studentRepository.findByStudentId(studentId).get();

	        Integer userId = student.getUserId();
	        Optional<User> user = userRepository.findByUserId(userId);

	        if (user.isPresent()) {
	            users.add(user);
	        }
	    }
		
		List<HttpEntity<Notification>> requests = new ArrayList<>();
	    
	    for (Optional<User> user : users) {
	        Notification notification = new Notification();
	        notification.setNotificationMessage(notificationMessage);
	        notification.setNotificationUserId(user.get().getUserId());
	        notification.setNotificationType(NotificationType.REGISTRATION);

	        requests.add(new HttpEntity<>(notification));
	    }
	    
	    return new RestTemplate()
	            .postForEntity(
	            		"http://localhost:8086/api/notification/send-all-notifications/REGISTRATION",
	                    requests,
	                    String.class);
	}
}
