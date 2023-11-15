package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.dto.CourseReqDto;
import com.shofiqul.interfaces.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/course")
@RequiredArgsConstructor
public class CourseController {
	private final CourseService courseService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createCourse(@RequestBody CourseReqDto dto) {
		return courseService.createCourse(dto);
	}
	
}
