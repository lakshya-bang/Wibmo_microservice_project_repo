/**
 * 
 */
package com.wibmo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.enums.CourseType;

/**
 * 
 */
public class CourseResponseDTOGenerator {
	private static final Random random = new Random();

	private static CourseResponseDTO generateCourseResponseDTO() {
        Integer courseId = random.nextInt(1000);
        String courseTitle = generateRandomString(10);
        Integer semester = random.nextInt(2) + 1;
        Integer year = random.nextInt(4) + 2020;
        String department = generateRandomString(5);
        Boolean isCancelled = random.nextBoolean();
        Integer noOfSeats = random.nextInt(50) + 1;
        CourseType courseType = CourseType.values()[random.nextInt(CourseType.values().length)];
        Integer professorId = random.nextInt(100);
        String professorEmail = generateRandomString(8) + "@example.com";
        String professorName = generateRandomString(6);

        return new CourseResponseDTO(courseId, courseTitle, semester, year, department, isCancelled, noOfSeats,
                courseType, professorId, professorEmail, professorName);
}
	private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }	
	public static List<CourseResponseDTO> getCourseResponseDTOList(Integer count){
		List<CourseResponseDTO> courseResponseDTOs = new ArrayList<CourseResponseDTO>();
		for(int i=0;i<count;i++) {
			courseResponseDTOs.add(generateCourseResponseDTO());
		}
		return courseResponseDTOs;
	}
}
