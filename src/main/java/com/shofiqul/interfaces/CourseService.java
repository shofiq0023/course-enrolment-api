package com.shofiqul.interfaces;

import org.springframework.http.ResponseEntity;

import com.shofiqul.dto.CourseDto;
import com.shofiqul.dto.CourseReqDto;

public interface CourseService {

	ResponseEntity<?> createCourse(CourseReqDto dto);

	ResponseEntity<?> getCourse(long courseId);

	ResponseEntity<?> getAllCourses();

	ResponseEntity<?> updateCourse(long courseId, CourseDto dto);

}