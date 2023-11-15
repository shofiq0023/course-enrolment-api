package com.shofiqul.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.interfaces.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/instructor")
@RequiredArgsConstructor
public class InstructorController {
	private final CourseService courseService;
	
	// Get all course by instructor id
}
