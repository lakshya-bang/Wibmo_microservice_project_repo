package com.wibmo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.StudentIdCannotBeEmptyException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Student getStudentById(Integer studentId) throws UserNotFoundException {
		if(!studentRepository.existsByStudentId(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
		return studentOptional.isPresent()
				? studentOptional.get()
				: null;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds) {
		logger.info("student ids: " + studentIds);
		//TODO: check if all studentIds Exists
		if(null == studentIds) {
			return new ArrayList<Student>();
		}
		List<Integer> nonExistingIds = 
			studentIds
				.stream()
				.filter(studentId -> !studentRepository.existsByStudentId(studentId))
				.collect(Collectors.toList());
		logger.info("User with id/ids: " + nonExistingIds + " does not exist/exists.");
		
		return studentRepository.findAllByStudentIdIn(studentIds);
	}

	@Override
	public void add(Student student) {
		if(null == student) {
			return;
		}
		studentRepository.save(student);
	}
	
	@Override
	public Boolean isStudentExistsById(Integer studentId){
		return studentRepository.existsByStudentId(studentId);
	}
	
	//To fill the DTO and avoid looping multiple times on student id in registered_course
//	table to get student details we are using Map instead of List
	@Override
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> studentIds) {
				return studentRepository
				.findAllByStudentIdIn(studentIds)
				.stream()
				.collect(Collectors.toMap(
						Student::getStudentId,
						Function.identity()));
	}
	
	@Override
	public Integer getCurrentSemesterByStudentId(Integer studentId) throws UserNotFoundException {
		if(!studentRepository.existsByStudentId(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		return studentRepository
				.findByStudentId(studentId)
				.get()
				.getCurrentSemester();
	}
	
	/***************************** Utility methods ********************************/
	
	private Integer getUserId(Integer studentId) {
		return studentRepository
				.findById(studentId)
				.get()
				.getUserId();
	}
	
}
