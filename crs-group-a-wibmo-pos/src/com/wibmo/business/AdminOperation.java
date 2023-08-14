package com.wibmo.business;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
import com.wibmo.enums.RegistrationStatus;

public interface AdminOperation {

	/**
	 * Fetches the Admins of the respective Ids
	 * @param ids
	 * @return
	 */
	public List<Admin> getAdminsByIds(Set<Long> ids);
	
	/**
	 * Adds the new Admin to the DB post approval from the Approving Admin
	 * @param admin
	 * @return
	 * 
	 * TODO: Should add Notification service to
	 * notify what admins were not added.
	 */
	public Integer addAdmins(List<Admin> admins);
	
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
	public void addCourseToCatalog(Course course);
	
	/**
	 * 
	 */
	public void assignCoursesToProfessor();
	
	public void generateReportCards();
	
	public void generateBills();
	
	public void notifyNewRegistration();
	
	public void notifyPendingDues();
}
