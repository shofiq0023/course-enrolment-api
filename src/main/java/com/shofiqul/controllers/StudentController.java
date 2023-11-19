package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.interfaces.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/student")
@RequiredArgsConstructor
public class StudentController {
	private final CourseService courseService;
	
	@GetMapping("/courses")
	public ResponseEntity<?> getAllCoursesByStatus() {
		return courseService.getAllCoursesByStatus();
	}
	
	@GetMapping("/courses/topic")
	public ResponseEntity<?> getCoursesByTopic(@RequestParam("topic") String topic) {
		return courseService.getCoursesByTopic(topic);
	}
}
