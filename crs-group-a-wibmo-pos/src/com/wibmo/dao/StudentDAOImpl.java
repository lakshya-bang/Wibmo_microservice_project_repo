/**
 * 
 */
package com.wibmo.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wibmo.constants.SQLConstants;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class StudentDAOImpl implements StudentDAO{
	
	private static volatile StudentDAOImpl instance = null;
	 private StudentDAOImpl() {
	    }
	    
	    public static StudentDAOImpl getInstance() {
	        if (instance == null) {
	        	//synchronized blocks thread safe the object
	            synchronized (StudentDAOImpl.class) {
	                instance = new StudentDAOImpl();
	            }
	        }
	        return instance;
	    }
	    
	    
	/*****************************************************************/
	    
	private static final Connection conn = DBUtils.getConnection();
	 
	/**
	 * a new user registers for a cours
	 * @param StudentId
	 * @param CourseIds
	 */
	 @Override
	 public boolean registerCourse(Long StudentId, List<Integer> CourseIds) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SQLConstants.COURSE_INSERT); 
			stmt.setLong(1, StudentId);
			String CourseIdsToString = String.join(",", CourseIds.toString());
			stmt.setString(2, CourseIdsToString);
			
			int rowInserted=stmt.executeUpdate();  
			System.out.println("Student: " + rowInserted + " course registered.");
			stmt.close();
			return rowInserted>0;
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }
			}
	}
	 /**
	  * Students view their grades
	  * @param StudentId
	  */
	@Override
	public Map<Integer, String> getGrade(Long studentId){
	    Map<Integer, String> grades = new HashMap<>();
	    PreparedStatement stmt = null;
	    try {
	        stmt = conn.prepareStatement(SQLConstants.GRADE_VIEW);
	        stmt.setLong(1, studentId);
	        
	        ResultSet resultSet = stmt.executeQuery();
	        
	        while (resultSet.next()) {
	            int courseId = resultSet.getInt("course_id");
	            String grade = resultSet.getString("grade");
	            grades.put(courseId, grade);
	        }
	        
	        resultSet.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return grades;
	}

//	public void getGrade(Long studentId) {
//		// TODO Auto-generated method stub
//		
//	}
	/**
	 * students already registered for a course can add more course
	 * @param courseId
	 * @param StudentId
	 */
	@Override
	public boolean addCourse(int courseId, Long StudentId) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SQLConstants.COURSE_INSERT); 
			stmt.setLong(1, StudentId);
			stmt.setInt(2, courseId);
			
			int rowInserted=stmt.executeUpdate();  
			System.out.println("Student: " + rowInserted + " course added.");
			stmt.close();
			return rowInserted>0;
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }
			}
	}
	/**
	 * Students already registered in courses can drop a course
	 * @param courseId
	 * @param StudentId
	 */
	@Override
	public boolean dropCourse(int courseId, Long StudentId) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SQLConstants.COURSE_DROP); 
			stmt.setLong(1, StudentId);
			stmt.setInt(2, courseId);
			
			int rowInserted=stmt.executeUpdate();  
			System.out.println("Student: " + rowInserted + " course dropped.");
			stmt.close();
			return rowInserted>0;
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }
			}
	}
	/**
	 * Students can view their registered courses
	 * @param StudentId
	 */
	@Override
	public void viewRegisteredCourses(Long StudentId) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SQLConstants.COURSE_DROP); 
		    ResultSet rs = stmt.executeQuery(SQLConstants.COURSE_DROP);
		      
		    String courses = "Courses Id", name = "NAME", semester="Semester";
		    System.out.format("%10s%16s%16s", courses, name, semester + "\n");
		    while(rs.next()){
		         int CoursesId  = rs.getInt("courses");
		         String name1 = rs.getString("name");
		         String semester1 = rs.getString("semester");

		         System.out.format("%10d%16s%16s", CoursesId, name1, semester1 + "\n");
		     }
		}catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }
		}
	}
	   
	
	
//	Map<Integer, String> Courses = Map.of( 101, "Maths", 102, "Physics",
//	103, "Chemistry", 104, "English", 105,
//	"Hindi", 109, "Sanskrit", 201, "History" );
//
//Map<Long, List<Integer>> registeredCourses = new HashMap<Long, List<Integer>>(Map.of(
//	1001L, List.of(101, 102, 103, 201, 109),
//	1002L, List.of(101, 105, 201, 104),
//	1003L, List.of(101, 105, 102),
//	1004L, List.of(101, 105, 201, 108)
//	));;
//
//Map<Long, Map<Integer, List<String>>> gradeCard = Map.of(
//    1001L, Map.of(1, List.of("Maths: A", "Physics: B", "Chemistry: A", "English: C", "Hindi: D", "History: B")),
//    1002L, Map.of(1, List.of("Maths: C", "Physics: B", "Chemistry: A", "English: A", "Hindi: B", "History: B")),
//    1003L, Map.of(2, List.of("Maths: A", "Physics: A", "Chemistry: B", "English: C", "Hindi: B", "History: D")),
//    1004L, Map.of(3, List.of("Maths: A", "Physics: A", "Chemistry: B", "English: C", "Hindi: D", "History: B"))
//    );

//	@Override
//	public boolean registerCourse(Long StudentId, List<Integer> CourseIds) {
//		// TODO Auto-generated method stub
//		 if (!registeredCourses.containsKey(StudentId)) {
//	            registeredCourses.put(StudentId, CourseIds);
//	            System.out.println("Student with id: " + StudentId + " is registered for the courses: ");
//	            for (Integer courseId : CourseIds) {
//	                String courseName = Courses.get(courseId);
//	                if (courseName != null) {
//	                    System.out.println(courseName);
//	                }
//	            }
//	            return true;
//	        }
//		 else {
//			 System.out.println("User is already registered and can choose to add/drop courses.");
//			 return false;
//		 }
//	}
//	
//	@Override
//	public void getGrade(Long studentId) {
//	    Map<Integer, List<String>> stGrades = gradeCard.get(studentId);
//	    if(stGrades != null) {
//	        System.out.println("Student " + studentId + " grades are: ");
//	        for (Map.Entry<Integer, List<String>> entry : stGrades.entrySet()) {
//	            int sem = entry.getKey();
//	            List<String> grades = entry.getValue();
//	            System.out.println("Semester " + sem + " grades: " + grades);
//	        }
//	    } else {
//	        System.out.println("Student grades are not available");
//	    }
//	}
//
//	@Override
//	public boolean addCourse(int courseId, Long StudentId) {
//		// TODO Auto-generated method stub
//		List<Integer> regCourses = registeredCourses.get(StudentId);
//		if(regCourses == null) {
//			regCourses.add(courseId);
//			System.out.println("Course was added Successfuly");
//		}
//		else if(regCourses!= null && !regCourses.contains(courseId)) {
//	    	regCourses.add(courseId);
//	        System.out.println("Course was added Successfuly");
//	        return true;
//	    }
//	    return false;
//	}
//
//	@Override
//	public boolean dropCourse(int courseId, Long StudentId) {
//		// TODO Auto-generated method stub
//		List<Integer> regCourses = registeredCourses.get(StudentId);
//		if(regCourses == null) {
//			System.out.println("Student " + StudentId + " was not enrolled in this course.");
//	        return true;
//		}
//	    if (regCourses != null && regCourses.contains(courseId)) {
//	    	regCourses.remove(Integer.valueOf(courseId));
//	        System.out.println("Course was removed Successfuly");
//	        return true;
//	    }
//	    return false;
//	}
//	
//	@Override
//	public void viewRegisteredCourses(Long StudentId) {
//		List<Integer> regCourses = registeredCourses.get(StudentId);
//	    if (regCourses != null) {
//	        System.out.println("Student " + StudentId + " registered courses: " + regCourses);
//	    } else {
//	        System.out.println("No Courses are registered for student with id: " + StudentId);
//	    }
//	}
}
