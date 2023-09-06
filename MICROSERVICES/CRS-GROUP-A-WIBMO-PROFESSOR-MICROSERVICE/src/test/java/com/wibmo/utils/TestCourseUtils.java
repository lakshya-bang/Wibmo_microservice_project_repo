/**
 * 
 */
package com.wibmo.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wibmo.entity.Course;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.repository.CourseRepository;

public class TestCourseUtils {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseUtils courseUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsCourseExistsInCatalog_CourseExists_ReturnsTrue() {
        Integer courseId = 1;
        when(courseRepository.existsByCourseId(courseId)).thenReturn(true);

        Boolean result = courseUtils.isCourseExistsInCatalog(courseId);

        assertTrue(result);
    }

    @Test
    public void testIsCourseExistsInCatalog_CourseDoesNotExist_ReturnsFalse() {
        Integer courseId = 1;
        when(courseRepository.existsByCourseId(courseId)).thenReturn(false);

        Boolean result = courseUtils.isCourseExistsInCatalog(courseId);

        assertFalse(result);
    }

    @Test
    public void testGetCourseIdToCourseMap_ValidCourseIds_ReturnsCourseMap() {
        List<Integer> courseIds = Arrays.asList(1, 2, 3);
        List<Course> courses = Arrays.asList(
                new Course(1, "Course 1",null,null,null,null,null,null, CourseType.PRIMARY),
                new Course(2, "Course 2",null,null,null,null,null,null, CourseType.ALTERNATIVE),
                new Course(3, "Course 3",null,null,null,null,null,null, CourseType.PRIMARY)
        );
        when(courseRepository.findAllByCourseIdIn(courseIds)).thenReturn(courses);

        Map<Integer, Course> result = courseUtils.getCourseIdToCourseMap(courseIds);

        assertEquals(3, result.size());
        assertEquals("Course 1", result.get(1).getCourseTitle());
        assertEquals("Course 2", result.get(2).getCourseTitle());
        assertEquals("Course 3", result.get(3).getCourseTitle());
    }

    @Test
    public void testGetCourseTypeByCourseId_CourseExists_ReturnsCourseType() throws CourseNotExistsInCatalogException {
        Integer courseId = 1;
        Course course = new Course(courseId, "Course 1",null,null,null,null,null,null, CourseType.PRIMARY);
        when(courseRepository.findByCourseId(courseId)).thenReturn(Optional.of(course));

        CourseType result = courseUtils.getCourseTypeByCourseId(courseId);

        assertEquals(CourseType.PRIMARY, result);
    }

    @Test
    public void testGetCourseTypeByCourseId_CourseDoesNotExist_ThrowsCourseNotExistsInCatalogException() {
        Integer courseId = 1;
        when(courseRepository.findByCourseId(courseId)).thenReturn(Optional.empty());

        assertThrows(CourseNotExistsInCatalogException.class, () -> {
            courseUtils.getCourseTypeByCourseId(courseId);
        });
    }
}
