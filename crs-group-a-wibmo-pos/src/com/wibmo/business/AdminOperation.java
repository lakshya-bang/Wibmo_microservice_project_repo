package com.wibmo.business;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
import com.wibmo.enums.RegistrationStatus;

public interface AdminOperation {

	public boolean logIn();
	/**
	 * 
	 * @param id
	 * @return
	 */
	// public Admin getAdminById(Long id);
	
	/**
	 * Fetches the Admins of the respective Ids
	 * @param ids
	 * @return
	 */
	// public List<Admin> getAdminsByIds(Set<Long> ids);
	
	/**
	 * Adds the new Admin to the DB post approval from the Approving Admin
	 * @param admin
	 * @return
	 * 
	 * TODO: Should add Notification service to
	 * notify what admins were not added.
	 */
	public boolean registerAdmin(String email, String password,String name);
	
	/**
	 * Allows the Admin to update the status of the Student Course Registration
	 * @param registrationId
	 * @return
	 */
	public RegistrationStatus acknowledgeStudentRegistration(Long registrationId, Integer semester);
	
	/**
	 * Allows the Admin to add the given course to the Course Catalog
	 * @param course
	 */
	public boolean addCourseToCatalog(Course course);
	
	public boolean dropCourseFromCatalog(String courseName);
	/**
	 * 
	 */
	public void assignCoursesToProfessor(String courseName,String professorName);
	
	public void generateReportCards();
	
	// public void generateBills();
	
	// public void notifyNewRegistration();
	
	// public void notifyPendingDues();
}
