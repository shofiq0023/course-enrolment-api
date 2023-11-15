package com.shofiqul.interfaces;

import org.springframework.http.ResponseEntity;

import com.shofiqul.dto.CourseReqDto;

public interface CourseService {

	ResponseEntity<?> createCourse(CourseReqDto dto);

}
