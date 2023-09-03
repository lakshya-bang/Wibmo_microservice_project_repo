package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wibmo.entity.Student;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
public interface StudentService {

	/**
	 * @param integer
	 * @return
	 * @throws UserNotFoundException 
	 */
	public Student getStudentById(Integer integer) throws UserNotFoundException;
	
	/**
	 * 
	 * @return
	 */
	public List<Student> getAllStudents();

	/**
	 * 
	 * @param student
	 */
	public void add(Student student);

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws UserNotFoundException 
	 */
	public Boolean isStudentExistsById(Integer studentId);
	
	/**
	 * 
	 * @param studentIds
	 * @return
	 * @throws UserNotFoundException 
	 */
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds);

	/**
	 * 
	 * @param collect
	 * @return
	 */
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> collect);

	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws UserNotFoundException 
	 */
	public Integer getCurrentSemesterByStudentId(Integer studentId) throws UserNotFoundException;

}	
