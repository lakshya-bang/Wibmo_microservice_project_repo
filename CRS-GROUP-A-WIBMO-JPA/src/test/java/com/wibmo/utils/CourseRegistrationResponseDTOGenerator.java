/**
 * 
 */
package com.wibmo.utils;

import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.enums.RegistrationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 
 */
public class CourseRegistrationResponseDTOGenerator {
    private static final Random random = new Random();

	public static CourseRegistrationResponseDTO generateCourseRegistrationResponseDTO() {
        Integer courseRegistrationId = random.nextInt(1000);
        Integer studentId = random.nextInt(1000);
        Integer semester = random.nextInt(10) + 1;
        List<CourseResponseDTO> primaryCourses = CourseResponseDTOGenerator.getCourseResponseDTOList(4);
        List<CourseResponseDTO> alternativeCourses = CourseResponseDTOGenerator.getCourseResponseDTOList(2);
        RegistrationStatus registrationStatus = generateRegistrationStatus();

        return new CourseRegistrationResponseDTO(
                courseRegistrationId,
                studentId,
                semester,
                primaryCourses,
                alternativeCourses,
                registrationStatus
        );
    }
	private static RegistrationStatus generateRegistrationStatus() {
        RegistrationStatus[] values = RegistrationStatus.values();
        int index = random.nextInt(values.length);
        return values[index];
    }
}
