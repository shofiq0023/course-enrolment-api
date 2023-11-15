package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.interfaces.CourseService;
import com.shofiqul.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminControllers {
	private final UserService userService;
	private final CourseService courseService;
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/courses")
	public ResponseEntity<?> getAllCourses() {
		return courseService.getAllCourses();
	}
}
