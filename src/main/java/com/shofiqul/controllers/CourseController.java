package com.shofiqul.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shofiqul.dto.CourseDto;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCourse(@PathVariable("id") long courseId) {
		return courseService.getCourse(courseId);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable("id") long courseId, @RequestBody CourseDto dto) {
		return courseService.updateCourse(courseId, dto);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable("id") long courseId) {
		return courseService.deleteCourse(courseId);
	}
}
