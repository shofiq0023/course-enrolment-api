package com.shofiqul.interfaces;

import org.springframework.http.ResponseEntity;

import com.shofiqul.dto.CourseDto;
import com.shofiqul.dto.CourseReqDto;
import com.shofiqul.dto.StudentEnrollmentReqDto;

public interface CourseService {

	ResponseEntity<?> createCourse(CourseReqDto dto);

	ResponseEntity<?> getCourse(long courseId);

	ResponseEntity<?> getAllCourses();

	ResponseEntity<?> updateCourse(long courseId, CourseDto dto);

	ResponseEntity<?> getAllCoursesByStatus();

	ResponseEntity<?> deleteCourse(long courseId);

	ResponseEntity<?> getCourseByInstructor(long instructorId);

	ResponseEntity<?> getInstructors();

	ResponseEntity<?> getCoursesByTopic(String topic);

	ResponseEntity<?> searchCourses(String searchText);

	ResponseEntity<?> studentEnrollment(StudentEnrollmentReqDto dto);

	ResponseEntity<?> studentDerollment(StudentEnrollmentReqDto dto);

	ResponseEntity<?> getCourseWithStudentInfo(long courseId);

}
