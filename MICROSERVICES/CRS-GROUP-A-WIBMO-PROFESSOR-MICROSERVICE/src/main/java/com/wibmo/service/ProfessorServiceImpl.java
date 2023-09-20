package com.wibmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.wibmo.repository.*;
import com.wibmo.utils.CourseRegistrationUtils;
import com.wibmo.utils.CourseUtils;
import com.wibmo.utils.JwtTokenUtil;
import com.wibmo.utils.StudentUtils;
import com.wibmo.converter.ReportCardConverter;
import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Professor;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CannotAddGradeStudentRegistrationNotApprovedException;
import com.wibmo.exception.CourseIdCannotBeEmptyException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.GradeCannotBeEmptyException;
import com.wibmo.exception.GradeValueInvalidException;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;
import com.wibmo.exception.StudentIdCannotBeEmptyException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
@Service
@Component
public class ProfessorServiceImpl implements ProfessorService {
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ProfessorRepository professorRepository;
	
	@Autowired
	CourseRegistrationRepository courseRegistrationRepository;
	
	@Autowired
	ReportCardRepository reportCardRepository;
	
	@Autowired
	CourseUtils courseUtils;
	
	@Autowired
	StudentUtils studentUtils;
	
	@Autowired
	CourseRegistrationUtils courseRegistrationUtils;
	
	@Autowired
	ReportCardConverter reportCardConverter;
	
	/**
	 * @param professorId (Integer)
	 * @return Professor
	 */

	@Override
	@Cacheable(value="professor")
	public Professor getProfessorById(Integer professorId) throws UserNotFoundException{
		if(!isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId,UserType.PROFESSOR);
		}
		
		return professorRepository
				.findAllByProfessorIdIn(Set.of(professorId))
				.get(0);
	}
	
	/**
	 * @param professorIds (Integer Set)
	 * @return Map<Integer,Professor>
	 */
	@Override
	@Cacheable(value="professor")
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds) {
		return professorRepository
				.findAllByProfessorIdIn(professorIds)
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId, 
						Function.identity()));
	}

	/**
	 * @param professorId (Integer)
	 * @return Boolean
	 */

	/**
	 * @param Professor
	 */
	@Override
	public void add(Professor professor) {
		
		// TODO
//		if(!userOperation.isUserExistsById(professor.getProfessorId())) {
//			
//		}
		
		professorRepository.save(professor);
		
		System.out.println("Account Registration sent to Admin for Approval.");
	}
	@Override
	@Cacheable(value="professor")
	public Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId)
			throws UserNotFoundException {
		
		
		if(null==professorId||!isProfessorExistsById(professorId)) {
			throw new UserNotFoundException(professorId, UserType.PROFESSOR);
		}
		
		
		Map<Integer, List<Student>> courseIdToRegisteredStudentsMap = new TreeMap<>();
		
		
		for(Tuple t:courseRegistrationRepository.getCourseIdToRegisteredStudentsMappingByProfessorId(professorId)) {
			
			Student student = new Student((int)t.get(0),(int)t.get(1),(String)t.get(2),(String)t.get(3),(int)t.get(4));
			if(courseIdToRegisteredStudentsMap.containsKey((int)t.get(5))) {
				courseIdToRegisteredStudentsMap.get((int)t.get(5)).add(student);
			}
			else {
				List<Student> tempStudent= new ArrayList<Student>();
				tempStudent.add(student);
				courseIdToRegisteredStudentsMap.put((int)t.get(5), tempStudent);
			}

		}

		return courseIdToRegisteredStudentsMap;
		
	}
	
	@Override
	public void addAll(List<ReportCardRequestDTO> reportCardRequestDTOs)
			throws
				StudentNotRegisteredForCourseException,
				CannotAddGradeStudentRegistrationNotApprovedException, 
				UserNotFoundException, 
				CourseNotExistsInCatalogException,
				StudentIdCannotBeEmptyException,
				CourseIdCannotBeEmptyException,
				GradeCannotBeEmptyException,
				GradeValueInvalidException,
				ProfessorNotAssignedForCourseException{
		
		if(null == reportCardRequestDTOs) {
			return;
		}
		
		/*************** Input Values Validations *****************/
		
		for(ReportCardRequestDTO reportCardRequestDTO : reportCardRequestDTOs) {
			
			Integer studentId = reportCardRequestDTO.getStudentId();
			Integer courseId = reportCardRequestDTO.getCourseId();
			String grade = reportCardRequestDTO.getGrade();
			if(studentId == null) {
				throw new StudentIdCannotBeEmptyException();
			}
			if(courseId == null) {
				throw new CourseIdCannotBeEmptyException();
			}
			if(grade == null) {
				throw new GradeCannotBeEmptyException();
			}
			if(!grade.matches("[abcdef|ABCDEF]")) {
				throw new GradeValueInvalidException();
			}
			if(!studentUtils.isStudentExistsById(studentId)) {
				throw new UserNotFoundException(studentId, UserType.STUDENT);
			}
			if(!courseUtils.isCourseExistsInCatalog(courseId)) {
				throw new CourseNotExistsInCatalogException(courseId);
			}
		}
		/***********************************************************************/
		
		// TODO: Should enhance via Join Query
		for(ReportCardRequestDTO reportCard : reportCardRequestDTOs) {
			
			Integer studentId = reportCard.getStudentId();
			Integer courseId = reportCard.getCourseId();
			Integer semester = studentUtils.getCurrentSemesterByStudentId(studentId);
			CourseRegistration courseRegistration = courseRegistrationUtils
					.getCourseRegistrationByStudentIdAndSemester(studentId, semester);
			if(RegistrationStatus.INVALID_REGISTRATION_STATUSES.contains(courseRegistration.getRegistrationStatus())) {
				throw new CannotAddGradeStudentRegistrationNotApprovedException(
						studentId, courseRegistration.getRegistrationStatus());
			}
			if(!courseRegistrationUtils.hasRegistrationByStudentIdAndCourseId(studentId, courseId)) {
				
				throw new StudentNotRegisteredForCourseException(studentId, courseId);
			}
			if(!checkIfProfessorTeaches(reportCard.getCourseId())) {
				String userName = jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));
				throw new ProfessorNotAssignedForCourseException(professorRepository.findByProfessorUserName(userName).getProfessorId(),reportCard.getCourseId());
			}
			
		}

		/***************************************************************************************/
		
		Map<Integer, Course> courseIdToCourseMap = courseUtils
				.getCourseIdToCourseMap(
						reportCardRequestDTOs
							.stream()
							.map(reportCardRequestDTO -> reportCardRequestDTO.getCourseId())
							.filter(Objects::nonNull)
							.collect(Collectors.toSet()));
		
		List<ReportCard> reportCards = new ArrayList<>();
		
		reportCardRequestDTOs
			.stream()
			.forEach(reportCardRequestDTO -> {
				Optional<ReportCard> reportCardOptional = reportCardRepository
						.findByStudentIdAndCourseId(
								reportCardRequestDTO.getStudentId(),
								reportCardRequestDTO.getCourseId());
				if(reportCardOptional.isPresent()) {
					ReportCard reportCard = reportCardOptional.get();
					reportCard.setGrade(reportCardRequestDTO.getGrade());
					reportCards.add(reportCard);
				} else {
					reportCards.add(
							reportCardConverter.convert(
									reportCardRequestDTO,
									courseIdToCourseMap));
				}
			});
		reportCardRepository.saveAll(reportCards);
		
	}
	@Cacheable(value="professor")
	private boolean isProfessorExistsById(Integer professorId) {
		// TODO Auto-generated method stub
		return professorRepository.existsById(professorId);
	}
	
	//**************************************************************************************************
	
	private boolean checkIfProfessorTeaches(Integer courseId) {
		String userName = jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));
		Integer userId = professorRepository.findByProfessorUserName(userName).getProfessorId();
		Integer userIdInCourse = courseRepository.findByCourseId(courseId).get().getProfessorId();
		if(userId.equals(userIdInCourse)) {
			return true;
		}
		return false;
	}
	
}
