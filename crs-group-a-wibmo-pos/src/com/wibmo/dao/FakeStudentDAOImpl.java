/**
 * 
 */
package com.wibmo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wibmo.bean.Course;
import com.wibmo.bean.Student;

/**
 * 
 */
public class FakeStudentDAOImpl implements StudentDAO{
	Map<Long, String> credential = Map.of(1001L, "user1", 1002L, "user2", 1003L, "user3", 1004L, "user4", 1004L, "user4");
	
	Map<Integer, String> Courses = Map.of( 101, "Maths", 102, "Physics",
			103, "Chemistry", 104, "English", 105,
			"Hindi", 109, "Sanskrit", 201, "History" );
	
	Map<Long, List<Integer>> registeredCourses = Map.of(
			1001L, List.of(101, 102, 103, 201, 109),
			1002L, List.of(101, 105, 201, 104),
			1003L, List.of(101, 105, 102),
			1004L, List.of(101, 105, 201, 108)
			);
	Map<Long, Map<Integer, List<String>>> gradeCard = Map.of(
		    1001L, Map.of(1, List.of("Maths: A", "Physics: B", "Chemistry: A", "English: C", "Hindi: D", "History: B")),
		    1002L, Map.of(1, List.of("Maths: C", "Physics: B", "Chemistry: A", "English: A", "Hindi: B", "History: B")),
		    1003L, Map.of(2, List.of("Maths: A", "Physics: A", "Chemistry: B", "English: C", "Hindi: B", "History: D")),
		    1004L, Map.of(3, List.of("Maths: A", "Physics: A", "Chemistry: B", "English: C", "Hindi: D", "History: B"))
		    );
	
	
	@Override
	public boolean logIn(Long StudentId, String Password) {
		// TODO Auto-generated method stub
		String pwd = credential.get(StudentId);
	    if (pwd != null && pwd.equals(Password)) {
	        System.out.println("LogIn Successful");
	        return true;
	    } else {
	        System.out.println("Invalid UserId and Password.");
	        return false;
	    }
	}

	@Override
	public boolean registerCourse(Long StudentId, List<Integer> CourseIds) {
		// TODO Auto-generated method stub
		 if (!registeredCourses.containsKey(studentId)) {
	            registeredCourses.put(studentId, courseIds);
	            System.out.println("Student with id: " + studentId + " is registered for the courses: ");
	            for (Integer courseId : courseIds) {
	                String courseName = Courses.get(courseId);
	                if (courseName != null) {
	                    System.out.println(courseName);
	                }
	            }
	            return true;
	        }
		 else {
			 System.out.println("User is already registered and can choose to add/drop courses.");
			 return false;
		 }
	}
	
	@Override
	public void getGrade(Long studentId) {
	    Map<Integer, List<String>> stGrades = gradeCard.get(studentId);
	    if(stGrades != null) {
	        System.out.println("Student " + studentId + " grades are: ");
	        for (Map.Entry<Integer, List<String>> entry : stGrades.entrySet()) {
	            int sem = entry.getKey();
	            List<String> grades = entry.getValue();
	            System.out.println("Semester " + sem + " grades: " + grades);
	        }
	    } else {
	        System.out.println("Student grades are not available");
	    }
	}

	@Override
	public boolean addCourse(int courseId, Long StudentId) {
		// TODO Auto-generated method stub
		List<Integer> regCourses = registeredCourses.get(StudentId);
		if(regCourses == null) {
			regCourses.add(courseId);
			System.out.println("Course was added Successfuly");
		}
		else (regCourses != null && !regCourses.contains(courseId)) {
	    	regCourses.add(courseId);
	        System.out.println("Course was added Successfuly");
	        return true;
	    }
	    return false;
	}

	@Override
	public boolean dropCourse(int courseId, Long StudentId) {
		// TODO Auto-generated method stub
		List<Integer> regCourses = registeredCourses.get(StudentId);
		if(regCourses == null) {
			System.out.println("Student " + StudentId + " was not enrolled in this course.");
	        return true;
		}
	    if (regCourses != null && regCourses.contains(courseId)) {
	    	regCourses.remove(Integer.valueOf(courseId));
	        System.out.println("Course was removed Successfuly");
	        return true;
	    }
	    return false;
	}
	
	@Override
	public void viewRegisteredCourses(Long StudentId) {
		List<Integer> regCourses = registeredCourses.get(StudentId);
	    if (regCourses != null) {
	        System.out.println("Student " + StudentId + " registered courses: " + regCourses);
	    } else {
	        System.out.println("No Courses are registered for student with id: " + StudentId);
	    }
	}
}
